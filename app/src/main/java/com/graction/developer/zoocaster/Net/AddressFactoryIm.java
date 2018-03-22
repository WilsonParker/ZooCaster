package com.graction.developer.zoocaster.Net;

import com.graction.developer.zoocaster.Model.Address.AddressModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by JeongTaehyun
 */

public interface AddressFactoryIm {

    /*
     * 주소 검색 API
     */
    @GET("maps/api/place/autocomplete/json")
    Call<AddressModel> searchAddress(@QueryMap Map<String, String> map);
}
