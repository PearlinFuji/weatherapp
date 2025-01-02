/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pearlin.weatherapp.utils.CustomDateSerializer;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author pearlin
 */
@Getter
@Builder
public class WeatherInfo <T> {
    private T weatherInfo;
    private String user;
    private String postalCode;
    
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date timestamp;
    
//    public WeatherInfo (T weatherInfo, String user, String postalCode, Date timestamp) {
//        this.user = user;
//        this.weatherInfo = weatherInfo;
//        this.postalCode = postalCode;
//        this.timestamp = timestamp;
//    }
//
//    public T getWeatherInfo() {
//        return weatherInfo;
//    }
//
//    public void setWeatherInfo(T weatherInfo) {
//        this.weatherInfo = weatherInfo;
//    }
//
//    public String getUserId() {
//        return user;
//    }
//
//    public void setUserId(String user) {
//        this.user = user;
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }
//
//    public String getPostalCode() {
//        return postalCode;
//    }
//
//    public void setPostalCode(String postalCode) {
//        this.postalCode = postalCode;
//    }
//
//    public Date getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(Date timestamp) {
//        this.timestamp = timestamp;
//    }
//    
    
}
