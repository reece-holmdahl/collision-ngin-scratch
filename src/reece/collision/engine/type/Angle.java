package reece.collision.engine.type;

import reece.collision.engine.util.Round;

public class Angle {

    // Instance Members
    private final double measure;
    private AngleUnit unit;

    /* CONSTRUCTORS */

    // Constructor: Measure AND Angle Unit
    public Angle(double measure, AngleUnit unit) {
        this.measure = measure;
        this.unit = unit;
    }

    // Constructor: Measure, Assumes Radians
    public Angle(double measure) {
        this(measure, AngleUnit.RADIANS);
    }

    // Angle Units
    public enum AngleUnit {
        RADIANS, DEGREES
    }

    /* CONTRACTS */

    public AngleUnit getUnit() {
        return unit;
    }

    public Angle getAngleIn(AngleUnit unit) {
        return new Angle(getMeasure(unit), unit);
    }

    public double getMeasure() {
        return measure;
    }
    public double getMeasure(AngleUnit inUnit) {
        switch(inUnit) {
            // If Not In Degrees, Return In Degrees
            case DEGREES:   if (unit != AngleUnit.DEGREES) {
                                return measure * 180 / Math.PI;
                            } else
                                return measure;

            // If Not In Radians, Return In Radians
            case RADIANS:   if (unit != AngleUnit.RADIANS)
                                return measure / 180 * Math.PI;
                            else
                                return measure;

            // If Not Specified, Return Measure
            default:        return measure;
        }
    }

    public double getRoundedMeasure() {
        return Round.round(measure);
    }
    public double getRoundedMeasure(AngleUnit inUnit) {
        return Round.round(getMeasure(inUnit));
    }
}