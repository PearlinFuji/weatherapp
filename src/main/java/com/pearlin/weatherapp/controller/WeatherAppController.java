/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.controller;

import com.pearlin.weatherapp.domain.WeatherHistory;
import com.pearlin.weatherapp.json.WeatherInfo;
import com.pearlin.weatherapp.response.ServiceResponse;
import com.pearlin.weatherapp.service.WeatherAppService;
import com.pearlin.weatherapp.service.WeatherAppServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.annotations.Tag;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pearlin
 */
@RestController
//@RequestMapping("/weather")
@Tag(name = "Weather History", description = "API to manage weather history data")
public class WeatherAppController {
    
    private WeatherAppService weatherAppService;

    @Autowired
    public void setWeatherAppService(WeatherAppServiceImpl weatherAppService) {
        this.weatherAppService = weatherAppService;
    }
    
    @GetMapping("/fetch")
    @Operation(summary = "Fetch Weather Data", description = "Fetch weather data for given postal code")
    public ServiceResponse<WeatherInfo> fetch(@RequestParam String postalCode, 
            @RequestParam String user) {
        ServiceResponse<WeatherInfo> weatherInfoRes = weatherAppService.fetchWeatherInformation(postalCode, user);
        return weatherInfoRes;
    }
    
    @GetMapping("/history")
    @Operation(summary = "Get Weather History", description = "Retrieve weather history by user and/or postal code")
    public ServiceResponse<List<WeatherHistory>> findByPostalCode(@RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String user) {
        ServiceResponse<List<WeatherHistory>> weatherHistoryRes = weatherAppService.getHistory(postalCode, user);
        return weatherHistoryRes;
    }
}
