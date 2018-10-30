package reece.collision.engine.util;

import reece.collision.engine.type.Angle;

import java.util.Comparator;

public class SortByAngle implements Comparator<Angle> {
    @Override
    public int compare(Angle o1, Angle o2) {
        int o1Deg = (int) (o1.getMeasure(Angle.AngleUnit.DEGREES) / Round.stdAccuracy);
        int o2Deg = (int) (o2.getMeasure(Angle.AngleUnit.DEGREES) / Round.stdAccuracy);
        return o1Deg - o2Deg;
    }
}