package reece.collision.engine.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Round {

    public static final int stdAccuracy = 3;

    private static final MathContext stdRound = new MathContext(stdAccuracy + 2, RoundingMode.HALF_UP);

    public static double round(double input) {
        return new BigDecimal(input, stdRound).doubleValue();
    }

    public static double round(double input, int decimalPlaces) {
        return new BigDecimal(input, new MathContext(decimalPlaces + 1, RoundingMode.HALF_UP)).doubleValue();
    }
}