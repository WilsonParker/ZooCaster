package com.graction.developer.zoocaster.Util;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil {
    public final static Charset ISO_8859_1 = Charset.forName("ISO-8859-1"), UTF_8 = Charset.forName("UTF-8"),
            DEFAULT = UTF_8;
    ;

    public static String getDefaultString(String s, String def) {
        if (nullCheck(s))
            return def;
        else
            return s;
    }

    public static boolean nullCheck(String s) {
        if (s == null)
            return true;
        s = s.trim();
        if (s.equals("") || s.equals("null"))
            return true;
        else
            return false;
    }

    public static boolean nullCheck(int i, boolean minus) {
        boolean result = false;
        if (minus)
            result = i > 0 ? false : true;
        else
            result = i == 0 ? true : false;
        return result;
    }

    // List to String
    public static <E> String createString(String root, String child, Collection<E> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("{" + root + " : { " + child + " [");
        for (E e : items)
            sb.append(e.toString() + ",");
        sb.append("]}}");
        return sb.toString();
    }

    public static <E> String createString(String root, Collection<E> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ " + root + " [");
        for (E e : items)
            sb.append(e.toString() + ",");
        sb.append("]}}");
        return sb.toString();
    }

    public static <E> String createString(String name, E[] es) {
        StringBuilder sb = new StringBuilder();
        sb.append("{" + name + " : [ ");
        for (E e : es)
            sb.append(e.toString() + ",");
        sb.append("]}");
        return sb.toString();
    }

    public static String requestParamEncoding(String str, Charset charset) {
        return new String(str.getBytes(ISO_8859_1), charset);
    }

    public static String requestParamEncoding(String str) {
        return requestParamEncoding(str, DEFAULT);
    }

    public static boolean contains(String s1, String s2, String delim) {
        StringTokenizer tokenizer = new StringTokenizer(s2, delim);
        while (tokenizer.hasMoreTokens()) {
            if (s1.contains(tokenizer.nextToken()))
                return true;
        }
        return false;
    }

    public static boolean contains(String s1, String s2) {
        return contains(s1, s2, " ");
    }

    public static String convertCase(String str, int index, boolean isUpper) {
        String s1 = str.substring(0, index),
                s2 = str.substring(index + 1, str.length());
        String s = str.charAt(index) + "";
        s = isUpper ? s.toUpperCase() : s.toLowerCase();
        return s1 + s + s2;
    }

    public static String convertFirstUpperOrLower(String str, boolean isUpper) {
        return convertCase(str, 0, isUpper);
    }

    public static String arrayToString(Object[] objects) {
        String result = objects[0].toString();
        for (int i = 1; i < objects.length; i++)
            result += "," + objects[i].toString();
        return result;
    }

    public static String arrayToString(int[] is) {
        String result = is[0] + "";
        for (int i = 1; i < is.length; i++)
            result += "," + is[i] + "";
        return result;
    }

    public static String arrayToString(List list) throws NullPointerException{
        if(list == null)
            throw new NullPointerException("List is null");
        String result = list.get(0).toString();
        for (int i = 1; i < list.size(); i++)
            result += "," + list.get(i).toString();
        return result;
    }

    public String decodeUnicode(String str) {
        String text = "";
        String strs[] = str.split(" ");
        for (String a : strs) {
            if (a.contains("\\u")) {
                String arr[] = a.split("\\\\u");
                for (int i = 1; i < arr.length; i++) {
                    int hexVal = Integer.parseInt(arr[i], 16);
                    text += (char) hexVal;
                }
            } else {
                text += " " + a;
            }
        }
        return text;
    }

    public String decodeUnicode2(String str) {
        String text = "";
        String arr[] = str.split(" ");
        for (String s : arr) {
            if (s.contains("\\u")) {
                int index = s.indexOf("\\u");
                while (index > -1) {
                    if (index > (s.length() - 6)) break;
                    int snum = index + 2, fnum = snum + 4;
                    String subStr = s.substring(snum, fnum);
                    text += (char) Integer.parseInt(subStr, 16);
                    index = str.indexOf("\\u", index + 1);
                }
            } else {
                text += " " + s;
            }
        }
        return text;
    }
}