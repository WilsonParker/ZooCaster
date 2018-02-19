package com.graction.developer.zoocaster.Net;

import com.graction.developer.zoocaster.Model.Address.AddressModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AddressFactoryIm {

    // Search Address
    /*
        Postcodify
        @GET("post/search.php")
        Call<PostcodifyModel> searchAddress(@QueryMap Map<String, String>map);
    */

    /*
        행정안전부
        @GET("addrlink/addrLinkApi.do")
        Call<AddressModelResult> searchAddress(@QueryMap Map<String, String> map);
    */

    @GET("maps/api/place/autocomplete/json")
    Call<AddressModel> searchAddress(@QueryMap Map<String, String> map);
}
