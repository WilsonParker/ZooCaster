package com.graction.develop.zoocaster;

import com.graction.developer.zoocaster.Util.Parser.MathematicsManager;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestAddress {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        test2();
    }

    String address = "대한민국 경기도 안양시 동안구 비산동 389-10";
    String regDo = "([가-힇]+(도)$)", regSi = "([가-힇]+(시)$)", regGu = "([가-힇]+(구)$)", regAd = "(^([가-힇]+(구))+(( |)([가-힣\\d\\(\\)_-]))*)";
    String[] regAddress = {regDo, regSi, regAd};
    String[] addressSplit = address.split(" ");
    String result = "";

    private void test1() {
        for (int i = 0; i < addressSplit.length; i++) {
            String ad = addressSplit[i];
            for (int j = 0; i < regAddress.length; j++) {
                boolean matches = ad.matches(regAddress[j]);
                if (matches) {
                    if (j == regAddress.length) {

                    } else {
                        result += ad;
                    }
//                    System.out.printf("%s : %s\n", ad, matches);
                }
            }
        }
        System.out.println(result);
    }

    RefStringFromArray refStringFromArray = new RefStringFromArray() {
        @Override
        public void getString(String s) {
            for (int i = 0; i < addressSplit.length; i++) {
                String ad = addressSplit[i];
                boolean matches = ad.matches(s);
                if (matches) {
                    System.out.printf("%s : %s / %d %d\n",s,ad,getIndex(), getLength());
                    if (getIndex() == getLength() - 1) {
                        refStringFromArray2.getStringFromArray(i, addressSplit);
                    } else {
                        result += ad+" ";
                    }
                }
            }
        }
    };

    RefStringFromArray refStringFromArray2 = new RefStringFromArray() {
        @Override
        public void getString(String s) {
            result += s+" ";
        }
    };

    private void test2() {
        refStringFromArray.getStringFromArray(0, regAddress);
        System.out.println(result);
    }

    public abstract class RefStringFromArray {
        private int index;
        private String[] srr;

        public int getIndex() {
            return index;
        }

        public int getLength() {
            return srr.length;
        }

        public void getStringFromArray(int start, String[] srr) {
            this.srr = srr;
            for (int i = start; i < srr.length; index = ++i)
                getString(srr[i]);
        }

        abstract void getString(String s);
    }
}