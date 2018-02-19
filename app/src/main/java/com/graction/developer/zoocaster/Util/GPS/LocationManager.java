package com.graction.developer.zoocaster.Util.GPS;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Util.Log.HLogger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hare on 2017-07-20.
 * Updated by Hare on 2017-09-27.
 */

public class LocationManager {
    private HLogger logger;
    private Context context;
    private AddressHandleListener addressHandleListener;

    public LocationManager(Context context, AddressHandleListener addressHandleListener) {
        this.context = context;
        this.addressHandleListener = addressHandleListener;
        logger = new HLogger(getClass());
    }

    public void getAddress(final Location location) {
        if (location == null)
            return;
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        logger.log(HLogger.LogType.INFO, "getAlarm_address(final Location location)", String.format("lat lon : %s, %s", lat, lon));
        if (location == null) return;
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);
        logger.log(HLogger.LogType.INFO, "getAlarm_address(final Location location)", "geocoder is not Null? " + (geocoder != null));
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            if (addresses != null && addresses.size() > 0) {
                for (Address address : addresses) {
                    logger.log(HLogger.LogType.INFO, "getAlarm_address(final Location location)", "Address toString : " + address.toString());
                    logger.log(HLogger.LogType.INFO, "getAlarm_address(final Location location)", "Address getThoroughfare: " + address.getThoroughfare());
                    logger.log(HLogger.LogType.INFO, "getAlarm_address(final Location location)", "Address line : " + address.getAddressLine(0));
                }
//                addressHandleListener.setAlarm_address(AddressManager.getInstance().getTransferAddr(addresses.get(0)));
                addressHandleListener.setAddress(addresses.get(0).getAddressLine(0));
            } else {
                logger.log(HLogger.LogType.INFO, "getAlarm_address(final Location location)", "Address is empty");
                addressHandleListener.setAddress("주소 결과가 없습니다");
            }
        } catch (IOException e) {
            logger.log(HLogger.LogType.ERROR, "getAlarm_address(final Location location)", "getAlarm_address Error", e);
            addressHandleListener.setAddress("주소 변환 실패");
        }

    }
}
