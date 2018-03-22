package com.graction.developer.zoocaster.Net;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Location;
import com.graction.developer.zoocaster.Model.Response.DailyForecast;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualitySingleModel;
import com.graction.developer.zoocaster.Model.Response.SimpleResponseModel;
import com.graction.developer.zoocaster.Model.VO.FineDustVO;
import com.graction.developer.zoocaster.Model.Response.Forecast5DayModel;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.zoocaster.Model.Response.WeatherModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by JeongTaehyun
 */
public interface NetFactoryIm {

	// Current weather
	@GET("app/current_weather")
	Call<WeatherModel> selectWeather(@Query(DataStorage.Key.KEY_LATITUDE) double lat, @Query(DataStorage.Key.KEY_LONGITUDE) double lon);

	// 16 daily weather forecast
	@GET("app/forecast_daily")
	Call<DailyForecast> selectForecastDaily(@Query(DataStorage.Key.KEY_LATITUDE) double lat, @Query(DataStorage.Key.KEY_LONGITUDE) double lon);

	// 5 day / 3 hour weather forecast
	@GET("app/forecast_5day")
	Call<Forecast5DayModel> selectForecast5Day(@Query(DataStorage.Key.KEY_LATITUDE) double lat, @Query(DataStorage.Key.KEY_LONGITUDE) double lon);

	/*
	 * call Integrated Air Quality
	 * 통합대기지수 API
	 */
	@GET("app/integratedAirQuality")
	Call<IntegratedAirQualitySingleModel> selectIntegratedAirQuality(@Query(DataStorage.Key.KEY_LATITUDE) double lat, @Query(DataStorage.Key.KEY_LONGITUDE) double lon);

	/*
	 * get activated fineDustType list
	 * 미세먼지 규격 API
	 */
	@GET("app/fine_dust_standard")
	Call<SimpleResponseModel<ArrayList<FineDustVO>>> selectFineDustStandard();

	/*
	 * 주소 -> 위도경도 변환 API
	 */
	@GET("app/getAddressFromLocation")
	Call<SimpleResponseModel<String>> getAddressFromLocation(@Query(DataStorage.Key.KEY_LATITUDE) double lat, @Query(DataStorage.Key.KEY_LONGITUDE) double lon);

	/*
	 * 위도경도 -> 주소 변환 API
	 */
	@GET("app/getLocationFromAddress")
	Call<SimpleResponseModel<Location>> getLocationFromAddress(@Query(DataStorage.Key.KEY_ADDRESS) String address);
}
