/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author pearlin
 */
@Component
public class WeatherInfoFetcher {

    @Value("${weather.api.url}")
    private String apiKey;

    @Value("${weather.api.key}")
    private String weatherApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode fetchWeatherInfo(String postalCode, String user) throws Exception {
        String url = weatherApiUrl + "?zip=" + postalCode + ",us&appid=" + apiKey + "&units=imperial";
        String response = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode weatherData = mapper.readTree(response);
        return weatherData;
    }
}
