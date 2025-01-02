/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.service;

import com.pearlin.weatherapp.domain.WeatherHistory;
import com.pearlin.weatherapp.json.WeatherInfo;
import com.pearlin.weatherapp.response.ServiceResponse;
import java.util.List;

/**
 *
 * @author pearlin
 */
public interface WeatherAppService {
    public ServiceResponse<WeatherInfo> fetchWeatherInformation (String postalCode, String user);
    public ServiceResponse<List<WeatherHistory>> getHistory (String postalCode, String user);
    public WeatherHistory saveWeatherInfo (WeatherHistory weatherHistory) throws Exception;
}
