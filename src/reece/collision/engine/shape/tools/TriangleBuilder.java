package reece.collision.engine.shape.tools;

import org.jetbrains.annotations.NotNull;
import reece.collision.engine.shape.Triangle;
import reece.collision.engine.type.Angle;

public class TriangleBuilder {

    /* Minimum Ways To Build A Triangle:
             B
           /  \
         /     \
       /        \
     A _________ C
     Format:    #. (Geometry Term, SSS, SSA, etc); (Sides and Angles Needed To Build, AB, BC, C) -> Formula To Use (Cosine Rule, Sine Rule, etc), Black Means Repeat
     Note:      A Sides Coordinating Angle Is Always The Letter Not Used In The Segment, AB -> C, BC -> A, etc

     1. SSS; AB, BC, CA -> Cosine Rule

     2. SAS; AB, B, BC  -> Cosine Rule
     3. SAS; BC, C, CA  ->
     4. SAS; CA, A, AB  ->

     5. SSA; AB, BC, C  -> Sine Rule
     6. SSA; BC, CA, A  ->
     7. SSA; CA, AB, B  ->

     8. AAS; A, B, BC   -> Sine Rule
     9. AAS; A, B, CA   ->
     10. AAS; B, C, CA  ->
     11. AAS; B, C, AB  ->
     12. AAS; C, A, AB  ->
     13. AAS; C, A, BC  ->
     */

    // Triangle Constructor: 1, SSS
    @NotNull
    public static Triangle makeSSS(double AB, double CA, double BC) {
        // Set Up Triangle Angles
        Angle A = cosineRule(AB, BC, CA);
        Angle B = sineRule(BC, A, CA);
        Angle C = new Angle(180 - A.getMeasure(Angle.AngleUnit.DEGREES) - B.getMeasure(Angle.AngleUnit.DEGREES), Angle.AngleUnit.DEGREES);

        // Construct Triangle
        return new Triangle(AB, CA, BC, C, B, A);
    }

    // Triangle Constructor: 2-4, SAS
    @NotNull
    public static Triangle makeSAS(double leftSide, Angle middleAngle, double rightSide) {
        // Set Up Triangle Sides
        double BC = cosineRule(leftSide, middleAngle, rightSide);

        // Set Up Triangle Angles
        Angle rightAngle = sineRule(BC, middleAngle, rightSide);
        Angle A = new Angle(180 - rightAngle.getMeasure(Angle.AngleUnit.DEGREES) - middleAngle.getMeasure(Angle.AngleUnit.DEGREES), Angle.AngleUnit.DEGREES);

        // Construct Triangle
        return new Triangle(leftSide, rightSide, BC, A, rightAngle, middleAngle);
    }

    // Triangle Constructor: 5-7, SSA
    @NotNull
    public static Triangle makeSSA(double comparativeSide, double oppositeAngleSide, Angle comparativeAngle) {
        // Set Up Triangle Angles
        Angle oppositeSideAngle = sineRule(comparativeSide, comparativeAngle, oppositeAngleSide);
        Angle remainingAngle = new Angle(180 - oppositeSideAngle.getMeasure(Angle.AngleUnit.DEGREES) - comparativeAngle.getMeasure(Angle.AngleUnit.DEGREES), Angle.AngleUnit.DEGREES);

        // Set Up Triangle Sides
        double remainingSide = sineRule(comparativeSide, comparativeAngle, remainingAngle);

        // Construct Triangle
        return new Triangle(comparativeSide, oppositeAngleSide, remainingSide, comparativeAngle, oppositeSideAngle, remainingAngle);
    }

    // Triangle Constructor: 8-13, AAS
    @NotNull
    public static Triangle makeAAS(Angle leftMostAngle, Angle middleAngle, double rightSide) {
        // Set Up Triangle Angles
        Angle remainingAngle = new Angle(180 - leftMostAngle.getMeasure(Angle.AngleUnit.DEGREES) - middleAngle.getMeasure(Angle.AngleUnit.DEGREES), Angle.AngleUnit.DEGREES);

        // Set Up Triangle Sides
        double middleAngleSide = sineRule(rightSide, leftMostAngle, middleAngle);
        double remainingSide = sineRule(middleAngleSide, middleAngle, remainingAngle);

        // Construct Triangle
        return new Triangle(rightSide, middleAngleSide, remainingSide, leftMostAngle, middleAngle, remainingAngle);
    }

    /* For Triangle:
             B
           /  \
         /     \
       /        \
     A _________ C
     You Have: CA, A, AB
     You Need: BC
     */
    private static double cosineRule(double leftSide, Angle middleAngle, double rightSide) {
        return Math.sqrt(leftSide * leftSide + rightSide * rightSide - (2 * leftSide * rightSide * Math.cos(middleAngle.getMeasure(Angle.AngleUnit.RADIANS))));
    }

    /* For Triangle:
             B
           /  \
         /     \
       /        \
     A _________ C
     You Have: AB, BC, CA
     You Need: A
     */
    private static Angle cosineRule(double leftSide, double oppAngleSide, double rightSide) {
        return new Angle(Math.acos((rightSide * rightSide + leftSide * leftSide - oppAngleSide * oppAngleSide) / (2 * rightSide * leftSide)));
    }

    /* For Triangle:
             B
           /  \
         /     \
       /        \
     A _________ C
     You Have: A, BC, B
     You Need: AC
     */
    private static double sineRule(double comparativeSide, Angle comparativeAngle, Angle oppSideAngle) {
        double compAngle = comparativeAngle.getMeasure(Angle.AngleUnit.RADIANS);
        double oppAngle = oppSideAngle.getMeasure(Angle.AngleUnit.RADIANS);
        return (comparativeSide * Math.sin(oppAngle)) / Math.sin(compAngle);
    }

    /* For Triangle:
             B
           /  \
         /     \
       /        \
     A _________ C
     You Have: A, BC, AC
     You Need: B
     */
    private static Angle sineRule(double comparativeSide, Angle comparativeAngle, double oppAngleSide) {
        double calcAngle = Math.asin(Math.sin(comparativeAngle.getMeasure(Angle.AngleUnit.RADIANS)) / comparativeSide * oppAngleSide);
        return new Angle(calcAngle);
    }
}