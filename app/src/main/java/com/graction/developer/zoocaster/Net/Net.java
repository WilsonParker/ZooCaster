package com.graction.developer.zoocaster.Net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Net {
	private static final Net instance = new Net();
//	private static final String BASE_URL = "http://10.0.2.2:8101/zoocaster/";
	public static final String BASE_URL = "http://192.168.0.8:8101/zoocaster/"
//								, ADDRESS_URL = "https://api.poesis.kr/";
//								, ADDRESS_URL = "http://www.juso.go.kr/";
								, ADDRESS_URL = "https://maps.googleapis.com";
	private NetFactoryIm netFactoryIm;
	private AddressFactoryIm addressFactoryIm;
	private Retrofit retrofit, addressRetrofit;
	
	{
		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		addressRetrofit = new Retrofit.Builder()
				.baseUrl(ADDRESS_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}

	public static Net getInstance() {
		return instance;
	}

	public NetFactoryIm getFactoryIm() {
		if (netFactoryIm == null)
			netFactoryIm = retrofit.create(NetFactoryIm.class);
		return netFactoryIm;
	}

	public AddressFactoryIm getAddressFactoryIm() {
		if (addressFactoryIm == null)
			addressFactoryIm = addressRetrofit.create(AddressFactoryIm.class);
		return addressFactoryIm;
	}
}
