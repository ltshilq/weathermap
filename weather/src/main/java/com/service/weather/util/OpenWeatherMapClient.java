package com.service.weather.util;

import com.service.weather.entity.objective.CurrentWeatherSummary;
import com.service.weather.entity.original.WeatherData;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpenWeatherMapClient
 */
public final class OpenWeatherMapClient
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentWeatherSummary.class);

    // Metric: Celsius, Imperial: Fahrenheit
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?appid=763d8bb819e1b0fb58c8385ddd26856e&units=metric&q=";

    public static CurrentWeatherSummary showCurrentWeather(String c)
    {
        String city = StringUtils.isNotBlank(c) ? c : "shenzhen,cn";

        CurrentWeatherSummary summary = new CurrentWeatherSummary();
        try
        {
            WeatherData weatherData = RestTemplateProxy.INSTANCE.getRestTemplate()
                    .getForObject(StringUtils.join(API_URL, city), WeatherData.class);

            summary.setCityName(weatherData.getName());
            summary.setCountry(weatherData.getSys().getCountry());
            summary.setTemperature(weatherData.getMain().getTemp());
            summary.setImage(weatherData.getWeather().get(0).getIcon());
            summary.setDate(weatherData.getDt());
            summary.setWeather(weatherData.getWeather().get(0).getDescription());
            summary.setWindSpeed(weatherData.getWind().getSpeed());
            summary.setCloudiness(weatherData.getWeather().get(0).getDescription());
            summary.setCloudsDeg(weatherData.getClouds().getAll());
            summary.setPressure(weatherData.getMain().getPressure());
            summary.setHumidity(weatherData.getMain().getHumidity());
            summary.setSunrise(weatherData.getSys().getSunrise());
            summary.setSunset(weatherData.getSys().getSunset());
            summary.setCoordinatesLon(weatherData.getCoord().getLon());
            summary.setCoordinatesLat(weatherData.getCoord().getLat());
        }
        catch (Exception e)
        {
            LOGGER.error("Failed to get the current weather data form OpenWeatherMap.", e);
        }

        return summary;
    }
}