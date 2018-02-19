package com.graction.developer.zoocaster.Net;

import com.graction.developer.zoocaster.Model.Response.DailyForecast;
import com.graction.developer.zoocaster.Model.Response.Forecast5DayModel;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.zoocaster.Model.Response.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetFactoryIm {

	// Current weather
	@GET("app/current_weather")
	Call<WeatherModel> selectWeather(@Query("lat") double lat, @Query("lon") double lon);

	// 16 daily weather forecast
	@GET("app/forecast_daily")
	Call<DailyForecast> selectForecastDaily(@Query("lat") double lat, @Query("lon") double lon);

	// 5 day / 3 hour weather forecast
	@GET("app/forecast_5day")
	Call<Forecast5DayModel> selectForecast5Day(@Query("lat") double lat, @Query("lon") double lon);

	// call Integrated Air Quality
	@GET("app/integratedAirQuality")
	Call<IntegratedAirQualityModel> selectIntegratedAirQuality(@Query("lat") double lat, @Query("lon") double lon);
}
