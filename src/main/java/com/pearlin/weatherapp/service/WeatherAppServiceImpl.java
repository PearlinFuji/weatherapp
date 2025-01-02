/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.pearlin.weatherapp.utils.PostalCodeValidator;
import com.pearlin.weatherapp.utils.Utils;
import com.pearlin.weatherapp.dao.WeatherHistoryDao;
import com.pearlin.weatherapp.domain.WeatherHistory;
import com.pearlin.weatherapp.json.WeatherInfo;
import com.pearlin.weatherapp.json.WeatherInfo.WeatherInfoBuilder;
import com.pearlin.weatherapp.response.ServiceResponse;
import com.pearlin.weatherapp.utils.WeatherInfoFetcher;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pearlin
 */
@Service
public class WeatherAppServiceImpl implements WeatherAppService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherAppServiceImpl.class);

    private PostalCodeValidator postalCodeValidator;
    private Utils utils;
    private WeatherInfoFetcher weatherInfoFetcher;
    private WeatherHistoryDao weatherHistoryDao;

    @Autowired
    public void setWeatherHistoryDao(WeatherHistoryDao weatherHistoryDao) {
        this.weatherHistoryDao = weatherHistoryDao;
    }

    @Autowired
    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    @Autowired
    public void setWeatherInfoFetcher(WeatherInfoFetcher weatherInfoFetcher) {
        this.weatherInfoFetcher = weatherInfoFetcher;
    }

    @Autowired
    public void setPostalCodeValidator(PostalCodeValidator postalCodeValidator) {
        this.postalCodeValidator = postalCodeValidator;
    }

    @Override
    public ServiceResponse<WeatherInfo> fetchWeatherInformation(String postalCode, String user) {
        ServiceResponse<WeatherInfo> weatherInfoRes = new ServiceResponse<>();

        try {
            if (user != null && !user.matches("[a-zA-Z]+")) {
                weatherInfoRes.addError(new IllegalArgumentException("User must only contain letters and no numbers"));
                return weatherInfoRes;
            }

            ServiceResponse<Boolean> isValidPostalCodeSr = postalCodeValidator.isValidUSPostalCode(postalCode);
            if (!isValidPostalCodeSr.getErrors().isEmpty()) {
                weatherInfoRes.addError(new IOException(isValidPostalCodeSr.getErrors().get(0).getMessage()));
                return weatherInfoRes;

            }
            Boolean isValidPostalCode = isValidPostalCodeSr.getResponse();
            if (!isValidPostalCode) {
                weatherInfoRes.addError(new RuntimeException("Invalid postal code"));
                return weatherInfoRes;
            }
            JsonNode weatherData = weatherInfoFetcher.fetchWeatherInfo(postalCode, user);
            WeatherInfoBuilder<Object> weatherInfo
                    = WeatherInfo.builder()
                            .user(user)
                            .postalCode(postalCode)
                            .weatherInfo(weatherData)
                            .timestamp(new Date());
            weatherInfoRes.setResponse(weatherInfo.build());
            saveWeatherInfo(utils.convertToWeatherHistory(weatherInfo.build()));
            return weatherInfoRes;
        } catch (Exception e) {
            e.printStackTrace;
            weatherInfoRes.addError(e);
        }
        return weatherInfoRes;
    }

    @Override
    public WeatherHistory saveWeatherInfo(WeatherHistory weatherHistory) throws Exception {
        return weatherHistoryDao.save(weatherHistory);
    }

    @Override
    public ServiceResponse<List<WeatherHistory>> getHistory(String postalCode, String user) {
        ServiceResponse<List<WeatherHistory>> weatherHistoryRes = new ServiceResponse();
        if (user != null && postalCode != null) {
            weatherHistoryRes.setResponse(weatherHistoryDao.findBy(postalCode, user));
        } else if (user != null && postalCode == null) {
            weatherHistoryRes.setResponse(weatherHistoryDao.findByUser(user));
        } else {
            weatherHistoryRes.setResponse(weatherHistoryDao.findByPostalcode(postalCode));
        }
        return weatherHistoryRes;
    }
}
