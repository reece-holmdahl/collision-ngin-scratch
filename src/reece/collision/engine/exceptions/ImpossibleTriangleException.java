package reece.collision.engine.exceptions;

public class ImpossibleTriangleException extends RuntimeException {

    public ImpossibleTriangleException() {
        super();
    }

    public ImpossibleTriangleException(String s) {
        super(s);
    }

    public ImpossibleTriangleException(Throwable c) {
        super(c);
    }

    public ImpossibleTriangleException(String s, Throwable c) {
        super(s, c);
    }
}