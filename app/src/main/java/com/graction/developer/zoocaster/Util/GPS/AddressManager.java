package com.graction.developer.zoocaster.Util.GPS;

import android.location.Address;

/**
 * Created by Hare on 2017-07-18.
 */

public class AddressManager {
    public static AddressManager addressManager;

    static {
        addressManager = new AddressManager();
    }

    public static AddressManager getInstance() {
        return addressManager;
    }

    //  Address => 시군구동 표시
    public String getTransferAddr(Address address) {
        if (address == null) return "";
        return String.format("%s %s %s", address.getAdminArea(), address.getLocality(), address.getThoroughfare());
    }
}
