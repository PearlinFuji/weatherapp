/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.utils;

import com.pearlin.weatherapp.domain.WeatherHistory;
import com.pearlin.weatherapp.json.WeatherInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author pearlin
 */
@Component
public class Utils {
    
    public <T> T createInstance(Class<T> t) throws Exception {
        T instance = null;
        instance = t.newInstance();
        return instance;
    }
    
    public WeatherHistory convertToWeatherHistory(WeatherInfo weatherInfo) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(weatherInfo, WeatherHistory.class);
    }
    
}
