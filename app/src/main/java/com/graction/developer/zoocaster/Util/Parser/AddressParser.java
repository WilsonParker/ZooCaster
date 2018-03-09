package com.graction.developer.zoocaster.Util.Parser;

import com.graction.developer.zoocaster.Util.StringUtil;

/**
 * Created by Graction06 on 2018-03-09.
 */

public class AddressParser {
    private static final AddressParser instance = new AddressParser();
    private final String RegDo = "([가-힇]+(도)$)", RegSi = "([가-힇]+(시)$)", regGu = "([가-힇]+(구)$)", RegAdr = "(^([가-힇]+(구))+(( |)([가-힣\\d\\(\\)_-]))*)";
    private final String[] RegAddress = {RegDo, RegSi, RegAdr};
    private final StringUtil.StringArrayManager refStringFromArray = new StringUtil.StringArrayManager() {

        @Override
        protected void getString(String s) {
            for (int i = 0; i < addressSplit.length; i++) {
                String ad = addressSplit[i];
                boolean matches = ad.matches(s);
                if (matches) {
                    if (getIndex() == getLength() - 1) {
                        refStringFromArray2.getStringFromArray(i, addressSplit);
                    } else {
                        result += ad+" ";
                    }
                }
            }
        }
    };

    private final StringUtil.StringArrayManager refStringFromArray2 = new StringUtil.StringArrayManager() {
        @Override
        public void getString(String s) {
            result += s+" ";
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
        return result;
    }
}
