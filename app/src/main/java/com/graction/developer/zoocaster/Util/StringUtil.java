package com.graction.developer.zoocaster.Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by JeongTaehyun
 */

public class StringUtil {
    public final static Charset ISO_8859_1 = Charset.forName("ISO-8859-1"), UTF_8 = Charset.forName("UTF-8"),
            DEFAULT = UTF_8;
    private static Map<String, String> urlEncodingMap;
    static {
        urlEncodingMap = new HashMap<>();
        urlEncodingMap.put(" ","%20");
    }

    /*
     * index 에 해당하는 문자의 case 변경
     */
    public static String convertCase(String str, int index, boolean isUpper) {
        String s1 = str.substring(0, index),
                s2 = str.substring(index + 1, str.length());
        String s = str.charAt(index) + "";
        s = isUpper ? s.toUpperCase() : s.toLowerCase();
        return s1 + s + s2;
    }

    // 문자의 첫 단어 case 변경
    public static String convertFirstUpperOrLower(String str, boolean isUpper) {
        return convertCase(str, 0, isUpper);
    }

    // int array to String
    public static String arrayToString(int[] is) {
        String result = is[0] + "";
        for (int i = 1; i < is.length; i++)
            result += "," + is[i] + "";
        return result;
    }

    // Integer 로 변경 가능한지 확인
    public static boolean isStrictlyNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0)
            return false;
        for (int i = 0; i < cs.length(); i++)
            if (!Character.isDigit(cs.charAt(i)))
                return false;
        return true;
    }

    public static abstract class StringArrayManager{
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
                if(!getString(srr[i]))
                    break;
        }

        /*
         * return boolean to
         * Continue : true
         * Break : false
         */
        protected abstract boolean getString(String s);
    }
}