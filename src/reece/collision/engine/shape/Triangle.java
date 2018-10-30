package reece.collision.engine.shape;

import reece.collision.engine.exceptions.ImpossibleTriangleException;
import reece.collision.engine.type.Angle;
import reece.collision.engine.type.Vector2;
import reece.collision.engine.util.Round;
import reece.collision.engine.util.SortByAngle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Made By: Reece Holmdahl
 * Notes:
 * Internal calculations and measurements are left unrounded.
 * All contracts are rounded to the stdAccuracy decimal place e.g. 4 would be equal to a point accuracy of 0.0001,
 * rounding up.
 */

public class Triangle {

    // Triangle Sides
    private final double    BC, CA, AB;

    // Triangle Angles
    private final Angle     A, B, C;

    // Triangle Angle Slopes
    private final double    slopeA, slopeB, slopeC;

    // Triangle Vertices
    private final Vector2   vertA, vertB, vertC;

    // Triangle Area, Perimeter, and Semiperimeter
    private final double        area, perim, semiperim;

    // Triangle Type (Acute, Right, Obtuse)
    private final TriangleType  type;

    // Constructor: Master
    public Triangle(double AB, double BC, double CA, Angle A, Angle B, Angle C) throws ImpossibleTriangleException {
        // Impossible Triangle Exception: Cannot build triangle out of lengths
        if (AB > CA + BC || AB < Math.abs(CA - BC)) {
            String errorMsg = "Cannot build triangle with side lengths " + AB + ", " + CA + ", " + BC + ".";
            throw new ImpossibleTriangleException(errorMsg);
        }

        // Impossible Triangle Exception: Cannot build triangle with more or less than 180 degrees
        double degA = A.getMeasure(Angle.AngleUnit.DEGREES);
        double degB = B.getMeasure(Angle.AngleUnit.DEGREES);
        double degC = C.getMeasure(Angle.AngleUnit.DEGREES);
        double angleTotal = degA + degB + degC;
        if (angleTotal != 180) {
            String errorMsg = "Cannot build triangle with " + (angleTotal < 180 ? "less" : "more") + " than 180 degrees. ";
            String angles   = "Angle A: " + degA + ", Angle B: " + degB + ", Angle C: " + degC + ".";
            throw new ImpossibleTriangleException(errorMsg + angles);
        }

        // Sort Triangle Sides
        List<Double> triangleSides = new ArrayList<>();
        triangleSides.add(AB);
        triangleSides.add(CA);
        triangleSides.add(BC);
        Collections.sort(triangleSides);

        this.BC = triangleSides.get(0);
        this.CA = triangleSides.get(1);
        this.AB = triangleSides.get(2);

        // Sort Triangle Angles
        List<Angle> triangleAngles = new ArrayList<>();
        triangleAngles.add(C);
        triangleAngles.add(B);
        triangleAngles.add(A);
        triangleAngles.sort(new SortByAngle());

        this.A = triangleAngles.get(0);
        this.B = triangleAngles.get(1);
        this.C = triangleAngles.get(2);

        // Find Triangle Angle Slopes
        double radA = this.A.getMeasure(Angle.AngleUnit.RADIANS);
        double radB = this.B.getMeasure(Angle.AngleUnit.RADIANS);
        double radC = this.C.getMeasure(Angle.AngleUnit.RADIANS);
        slopeA = Math.tan(radA);
        slopeB = Math.tan(radA + radB);
        slopeC = Math.tan(radA + radB + radC);

        // Find Triangle Vertices
        double xVertB = Math.sqrt((AB * AB) / (slopeA * slopeA + 1));
        vertA = new Vector2(0, 0);
        vertB = new Vector2(xVertB, xVertB * slopeA);
        vertC = new Vector2(CA, 0);

        // Find Triangle Area
        area = this.BC * this.CA * Math.sin(this.C.getMeasure(Angle.AngleUnit.RADIANS)) / 2;

        // Find Triangle Perimeter and Semiperimeter
        perim       = this.BC + this.CA + this.AB;
        semiperim   = perim / 2;

        // Determine Triangle Type
        if (this.AB * this.AB > this.CA * this.CA + this.BC * this.BC) {
            type = TriangleType.OBTUSE;
        } else if (this.AB * this.AB < this.CA * this.CA + this.BC * this.BC) {
            type = TriangleType.ACUTE;
        } else {
            type = TriangleType.RIGHT;
        }
    }

    public enum TriangleType {
        ACUTE, RIGHT, OBTUSE
    }

    /* CONTRACTS */

    // Contracts Related To Angle A
    public double   getBC() {
        return Round.round(BC);
    }
    public Angle    getA() {
        return A;
    }
    public double   getSlopeA() {
        return Round.round(slopeA);
    }
    public Vector2  getVertA() {
        return vertA;
    }

    // Contracts Related To Angle B
    public double   getCA() {
        return Round.round(CA);
    }
    public Angle    getB() {
        return B;
    }
    public double   getSlopeB() {
        return Round.round(slopeB);
    }
    public Vector2  getVertB() {
        return vertB;
    }

    // Contracts Related To Angle C
    public double   getAB() {
        return Round.round(AB);
    }
    public Angle    getC() {
        return C;
    }
    public double   getSlopeC() {
        return Round.round(slopeC);
    }
    public Vector2  getVertC() {
        return vertC;
    }

    // Extraneous Contacts
    public double       getArea() {
        return Round.round(area);
    }
    public double       getPerim() {
        return Round.round(perim);
    }
    public double       getSemiperim() {
        return Round.round(semiperim);
    }
    public TriangleType getTriangleType() {
        return type;
    }
    public void         print() {
        // Angle A Info
        System.out.println("Angle A:");
        System.out.println("Side a:\t\t\t\t" + getBC());
        System.out.println("Angle A:\t\t\t" + getA().getRoundedMeasure(Angle.AngleUnit.DEGREES));
        System.out.println("Slope of A\t\t\t" + getSlopeA());
        System.out.println("Vertex of Point A:\t(" + getVertA().getRoundedA() + ", " + getVertA().getRoundedB() + ")\n");

        // Angle B Info
        System.out.println("Angle B:");
        System.out.println("Side b:\t\t\t\t" + getCA());
        System.out.println("Angle B:\t\t\t" + getB().getRoundedMeasure(Angle.AngleUnit.DEGREES));
        System.out.println("Slope of B\t\t\t" + getSlopeB());
        System.out.println("Vertex of Point B:\t(" + getVertB().getRoundedA() + ", " + getVertB().getRoundedB() + ")\n");

        // Angle C Info
        System.out.println("Angle C:");
        System.out.println("Side c:\t\t\t\t" + getAB());
        System.out.println("Angle C:\t\t\t" + getC().getRoundedMeasure(Angle.AngleUnit.DEGREES));
        System.out.println("Slope of C\t\t\t" + getSlopeC());
        System.out.println("Vertex of Point C:\t(" + getVertC().getRoundedA() + ", " + getVertC().getRoundedB() + ")\n");

        // Other Triangle Info
        System.out.println("Triangle:");
        System.out.println("Area:\t\t\t\t" + getArea());
        System.out.println("Perimeter:\t\t\t" + getPerim());
        System.out.println("Semiperimeter:\t\t" + getSemiperim());
        System.out.println("Triangle Type:\t\t" + getTriangleType().toString());
    }
}