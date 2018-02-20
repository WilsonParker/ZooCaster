package com.graction.develop.zoocaster;

import com.graction.developer.zoocaster.Util.Parser.MathematicsManager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestInteger {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        test2();
    }

    private void test1() {
        MathematicsManager math = MathematicsManager.getInstance();

        System.out.println((double) math.rounds((double) 5000 / 45, 5));
        System.out.println((double) math.rounds(45 / 5000, 5));

        System.out.println((double) 5000 / 45);
        System.out.println((double) 5000 / 45 * 1000);
        System.out.println((double) 5000 / 45 * 1000 / 1000);
    }

    private void test2() {
        System.out.println(roundsParseDouble((double) 5000 / 100, 5));
        System.out.println(roundsParseDouble(50 / roundsParseDouble((double) 5000 / 100, 5), 5));
    }

    public double roundsParseDouble(double d, double digit) {
        System.out.println("d : " + d);
        double p = Math.pow(10, digit), roundNum = d * p, upNum = roundNum % 10;
        System.out.println("d : " + upNum);
        if (upNum > 4) {
            roundNum += 10;
        }
        roundNum -= upNum;
        return roundNum / p;
    }
}