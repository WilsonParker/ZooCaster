package com.graction.developer.zoocaster.Util.Parser;

import com.graction.developer.zoocaster.Util.StringUtil;

/**
 * Created by Graction06 on 2018-03-09.
 */

public class AddressParser {
    private static final AddressParser instance = new AddressParser();
    private final String RegDo = "([가-힇]+(도)$)", RegSi = "([가-힇]+(시)$)", RegGu = "([가-힇]+(구)$)", RegDong = "([가-힇0-9]+(동)$)", RegUb = "([가-힇0-9]+(읍)$)", RegGun = "([가-힇0-9]+(군)$)", RegAdr = "(^([가-힇]+(구))+(( |)([가-힣\\d\\(\\)_-]))*)"
            , RegAdrAll = "(^([가-힇0-9]+(동)$))", RegAdrAll2 = "(^([가-힇]+(구))$)", RegAdrAll3 = "(^([가-힇0-9]+(군)$))", RegAdrAll4 = "(^([가-힇0-9]+(읍)$))";
    private final String[] RegAddress = {RegDo, RegSi, RegGu, RegDong, RegGun}
                            , AddressAddAll = {RegAdrAll3, RegAdrAll4, RegAdrAll, RegAdrAll2};
    private final StringUtil.StringArrayManager refStringFromArray = new StringUtil.StringArrayManager() {

        @Override
        protected boolean getString(String s) {
            for (int i = 0; i < addressSplit.length; i++) {
                String ad = addressSplit[i];
                boolean matches = ad.matches(s);
                if (matches) {
                    result += ad+" ";
                    break;
                }
            }
            return true;
        }
    }

    , refStringFromArray2 = new StringUtil.StringArrayManager() {
        @Override
        public boolean getString(String s) {
            result += s+" ";
            return true;
        }
    }

    , refStringFromArrayAddAll = new StringUtil.StringArrayManager() {
        @Override
        public boolean getString(String s) {
            for (int i = 0; i < addressSplit.length; i++) {
                String ad = addressSplit[i];
                boolean matches = ad.matches(s);
                if (matches) {
                    refStringFromArray2.getStringFromArray(i+1, addressSplit);
                    return false;
                }
            }
            return true;
        }
    };

    private String[] addressSplit;
    private String result;

    private AddressParser(){}

    public static AddressParser getInstance() {
        return instance;
    }

    public String parseAddress(String address){
        result = "";
        addressSplit = address.split(" ");
        refStringFromArray.getStringFromArray(0, RegAddress);
        refStringFromArrayAddAll.getStringFromArray(0, AddressAddAll);
        return result;
    }
}
