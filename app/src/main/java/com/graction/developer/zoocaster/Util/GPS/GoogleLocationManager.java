package com.graction.developer.zoocaster.Util.GPS;

import android.location.Location;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.UI.HandlerManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;

import java.io.IOException;

/**
 * Created by Hare on 2017-07-20.
 * Updated by Hare on 2017-09-27.
 */
public class GoogleLocationManager {
    private static final String APP_KEY = "AIzaSyDEp2qM4c-J2IjdDlBPobIEaig_x7lHuKw";
    private static String LANGUAGE = "ko";
    private final HLogger logger;
    private Thread thread;
    private HandlerManager handlerManager = HandlerManager.getInstance();
    private AddressHandleListener addressHandleListener;

    public GoogleLocationManager(AddressHandleListener addressHandleListener) {
        this.addressHandleListener = addressHandleListener;
        logger = new HLogger(getClass());
    }

    public void getAddress(Location location) {
        getAddress(location.getLatitude() + "", location.getLongitude() + "");
    }

    public void getAddress(final String lat, final String lon) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder();
                LatLng latlng = new LatLng(lat, lon);
                GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(latlng).setLanguage(LANGUAGE)
                        .getGeocoderRequest();
                GeocodeResponse geocoderResponse;
                try {
                    geocoderResponse = geocoder.geocode(geocoderRequest);
                    geocoder.geocode(geocoderRequest);
                    final GeocoderResult geocoderResult = geocoderResponse.getResults().iterator().next();
                    logger.log(HLogger.LogType.INFO, "getAlarm_address(final String lat, final String lon)", geocoderResult.getFormattedAddress());
                    handlerManager.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            addressHandleListener.setAddress(geocoderResult.getFormattedAddress());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.log(HLogger.LogType.ERROR, "getAlarm_address(final String lat, final String lon)", e);
                    addressHandleListener.setAddress("주소 결과가 없습니다");
                }
            }
        });
        thread.start();
    }
}

/*
500	light rain
501	moderate rain
502	heavy intensity rain
503	very heavy rain
504	extreme rain
511	freezing rain
520	light intensity shower rain
521	shower rain
522	heavy intensity shower rain
531	ragged shower rain

600	light snow
601	snow
602	heavy snow
611	sleet
612	shower sleet
615	light rain and snow
616	rain and snow
620	light shower snow
621	shower snow
622	heavy shower snow

701	mist
711	smoke
721	haze
731	sand, dust whirls
741	fog
751	sand
761	dust
762	volcanic ash
771	squalls
781	tornado
800	clear sky
801	few clouds
802	scattered clouds
803	broken clouds
804	overcast clouds
900	tornado
901	tropical storm
902	hurricane
903	cold
904	hot
905	windy
906	hail
951	calm
952	light breeze
953	gentle breeze
954	moderate breeze
955	fresh breeze
956	strong breeze
957	high wind, near gale
958	gale
959	severe gale
960	storm
961	violent storm
962	hurricane
 */