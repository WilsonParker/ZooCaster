package com.graction.developer.zoocaster.Util;

/**
 * Created by Graction06 on 2018-02-02.
 */

public class NullChecker {
    private static final NullChecker instance = new NullChecker();

    public static NullChecker getInstance() {
        return instance;
    }

    public boolean isNull(String str) {
        if (str == null || str == "" || str.equals(""))
            return true;
        else
            return false;
    }
}
