package reece.collision.engine.type;

import reece.collision.engine.util.Round;

public class Vector2 {

    private final double a, b;

    public Vector2(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }

    public double getRoundedA() {
        return Round.round(a);
    }
    public double getRoundedB() {
        return Round.round(b);
    }
}