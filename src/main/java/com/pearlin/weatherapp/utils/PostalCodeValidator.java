/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.utils;

import com.pearlin.weatherapp.response.ServiceResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Component;

/**
 *
 * @author pearlin
 */
@Component
public class PostalCodeValidator {
    
    public ServiceResponse<Boolean> isValidUSPostalCode (String postalCode) {
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        try {
            URL url = new URL("https://api.zippopotam.us/us/" + postalCode);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            response.setResponse(responseCode == 200);
            return response;  
        } catch (IOException e) {
            response.addError(e);
            return response;  
        }
    }
}
