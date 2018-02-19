package com.graction.developer.zoocaster.Util.Parser;

/**
 * Created by Graction06 on 2018-01-17.
 */

public class MathematicsManager {
    private static final MathematicsManager instance = new MathematicsManager();

    enum Operator {
        ADD, MINUS, MULTIPLY, DIVISION
    }


    public static MathematicsManager getInstance() {
        return instance;
    }

    public int rounds(double d, double digit) {
        double p = Math.pow(10, digit);
        return (int) Math.round(Math.round((d * p)) / p);
    }


    public int rounds(String d, double digit) {
        double p = Math.pow(10, digit);
        return (int) Math.round(Math.round((Double.parseDouble(d) * p)) / p);
    }
}
