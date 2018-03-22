package com.graction.developer.zoocaster.Util.Parser;

/**
 * Created by JeongTaehyun on 2018-01-17.
 */
public class MathematicsManager {
    private static final MathematicsManager instance = new MathematicsManager();

    public static MathematicsManager getInstance() {
        return instance;
    }

    public int rounds(double d, double digit) {
        double p = Math.pow(10, digit);
        return (int) (Math.round(d * p) / p);
    }

    public double roundsParseDouble(double d, double digit) {
        double p = Math.pow(10, digit), roundNum = d * p, upNum = roundNum % 10;
        if (upNum > 4)
            roundNum += 10;
        roundNum -= upNum;
        return roundNum / p;
    }

    public int rounds(String d, double digit) {
        double p = Math.pow(10, digit);
        return (int) Math.round(Math.round((Double.parseDouble(d) * p)) / p);
    }
}
