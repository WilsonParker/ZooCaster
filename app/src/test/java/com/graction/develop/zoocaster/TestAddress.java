package com.graction.develop.zoocaster;

import com.graction.developer.zoocaster.Util.Parser.AddressParser;
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
        test4();
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

    private void test3(){
        String testAddr = "안양시 관양동 인덕원사거리";
        String newAddress = AddressParser.getInstance().parseAddress(testAddr);
        System.out.println(newAddress);
    }

    private void test4(){
        String[] addresses = {
                "대한민국 경기도 안양시 동안구 비산동 389-10"
                , "대한민국 경기도 안양시 동안구 389-10"
                , "대한민국 경기도 안양시 비산동 389-10"
                , "대한민국 경기도 안양시 비산동"
                , "경기도 안양시 비산동"
                , "안양시 비산동"
                , "안양시 관양동 인덕원사거리"
                , "대한민국 광주광역시 북구 일곡동 일곡동 주민센터"
                , "대한민국 광주광역시 북구 일곡동 ８４９−５"
                , "대한민국 광주광역시 북구 일곡동 849−5"
                , "대한민국 광주광역시 북구 일곡동 일곡동 주민센터"
                , "대한민국 광주광역시 북구 일곡동 주민센터 849-5"
                , "대한민국 광주광역시 북구 일곡동 ８４９−５"
                , "대한민국 강원도 고성군"
                , "대한민국 강원도 화천군 화천읍 화천새싹길 강원도 화천군청"
                , "대한민국 전라남도 무안군 삼향읍 오룡길 전라남도청"
        };
        for(String address : addresses){
            String newAddress = AddressParser.getInstance().parseAddress(address);
            System.out.println(newAddress);
        }
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