package reece.collision.engine;

import reece.collision.engine.shape.Triangle;
import reece.collision.engine.shape.tools.TriangleBuilder;
import reece.collision.engine.type.Angle;

public class Main {

    /*
           B
          /  \
         /     \
        /        \
       A___________C
     */
    double triangleVertices[] = {};     //A, B, C

    public static void main(String args[]) {
        Triangle t = TriangleBuilder.makeSSS(19, 12, 14);
        t.print();
    }

    static void makeTriangle(double AB, double BC, double CA) {
        // cos(A) = (b^2 + c^2 - a^2)/(2*b*c)
        double angleA = Math.acos((BC * BC + CA * CA - AB * AB) / (2 * BC * CA));
        double slopeAB = Math.tan(angleA);
        // 3 point precision calculating
        double heightAB = 0;
        double hypo2 = Math.pow(AB, 2);
        for (double i = 0; Math.abs(hypo2 - i * i - Math.pow(slopeAB * i, 2)) > 0.0001; i += 0.0001) {
            heightAB = slopeAB * i;
        }
        System.out.println("For Triangle ABC:");
        System.out.println("       B\n      /  \\\n     /     \\\n    /        \\\n   A___________C");
        System.out.println("Angle A = " + Math.toDegrees(angleA));
        System.out.println("Slope of AB = " + slopeAB);
        System.out.println("Height of AB = " + triangleCoordSideHeight(AB, slopeAB, 0.001));
    }

    static double round(double input, double roundTo) {
        return (double) ((int) (input / roundTo)) * roundTo;
    }

    private static double triangleCoordSideHeight(double sideLength, double sideSlope, double accuracy) {
        double sideSq = sideLength * sideLength;
        double sideHeight = 0;
        for (double x = 0; Math.abs(sideSq - x * x - Math.pow(sideSlope * x, 2)) > accuracy; x += accuracy) {
            sideHeight = sideSlope * x;
        }
        return sideHeight;
    }
}