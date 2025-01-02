/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.service;

/**
 *
 * @author pearlin
 */
import com.pearlin.weatherapp.dao.WeatherHistoryDao;
import com.pearlin.weatherapp.domain.WeatherHistory;
import com.pearlin.weatherapp.json.WeatherInfo;
import com.pearlin.weatherapp.json.WeatherInfo.WeatherInfoBuilder;
import com.pearlin.weatherapp.response.ServiceResponse;
import com.pearlin.weatherapp.utils.PostalCodeValidator;
import com.pearlin.weatherapp.utils.WeatherInfoFetcher;
import com.pearlin.weatherapp.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(MockitoJUnitRunner.class)
class WeatherAppServiceImplTest {

    @InjectMocks
    private WeatherAppServiceImpl weatherAppService;

    @Mock
    private WeatherHistoryDao weatherHistoryDao;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PostalCodeValidator postalCodeValidator;
    
     @Mock
    private ServiceResponse<Boolean> isValidPostalCodeSr;
     
     @Mock
     private WeatherInfoFetcher weatherInfoFetcher;
     
     @Mock
     private Utils utils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchWeatherInformation_validScenario() throws Exception {
        String postalCode = "12345";
        String user = "JohnDoe";
        String responseJson = "{ \"main\": {\"temp\": 70}}";  
        ObjectMapper mapper = new ObjectMapper();
        WeatherInfoBuilder<Object> weatherInfo = WeatherInfo.builder().user(user).postalCode(postalCode).weatherInfo(responseJson).timestamp(new Date());
        when(postalCodeValidator.isValidUSPostalCode(postalCode)).thenReturn(isValidPostalCodeSr);
        when(isValidPostalCodeSr.getResponse()).thenReturn(true);
        when(isValidPostalCodeSr.getErrors()).thenReturn(Collections.emptyList());

        when(weatherInfoFetcher.fetchWeatherInfo(postalCode, user)).thenReturn(mapper.readTree(responseJson));

        when(utils.convertToWeatherHistory(weatherInfo.build())).thenReturn(new WeatherHistory());

        ServiceResponse<WeatherInfo> response = weatherAppService.fetchWeatherInformation(postalCode, user);

        assertNotNull(response.getResponse());
        assertTrue(response.getErrors().isEmpty());  
        assertEquals(user, response.getResponse().getUser()); 
    }

    @Test
    void testFetchWeatherInformation_InvalidPostalCode() {
        String postalCode = "33601";
        String user = "JohnDoe";

        ServiceResponse<Boolean> invalidPostalCodeResponse = new ServiceResponse<>();
        invalidPostalCodeResponse.addError(new RuntimeException("Invalid postal code"));
        invalidPostalCodeResponse.setResponse(false);

        when(postalCodeValidator.isValidUSPostalCode(postalCode)).thenReturn(invalidPostalCodeResponse);

        ServiceResponse<WeatherInfo> response = weatherAppService.fetchWeatherInformation(postalCode, user);

        assertNotNull(response);
        assertTrue(response.getErrors().size() > 0);
        assertEquals("Invalid postal code", response.getErrors().get(0).getMessage());
    }

    @Test
    void testFetchWeatherInformation_Exception() {
        String postalCode = "33601";
        String user = "JohnDoe";
        ServiceResponse<Boolean> validPostalCodeResponse = new ServiceResponse<>();
        validPostalCodeResponse.addError(new RuntimeException("API Error"));
        when(postalCodeValidator.isValidUSPostalCode(postalCode)).thenReturn(validPostalCodeResponse);
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException("API Error"));

        ServiceResponse<WeatherInfo> response = weatherAppService.fetchWeatherInformation(postalCode, user);

        assertNotNull(response);
        assertTrue(response.getErrors().size() > 0);
        assertEquals("API Error", response.getErrors().get(0).getMessage());
    }

    @Test
    void testGetHistory_ByPostalCodeAndUser() {
        String postalCode = "33601";
        String user = "JohnDoe";
        List<WeatherHistory> mockHistory = new ArrayList<>();

        when(weatherHistoryDao.findBy(postalCode, user)).thenReturn(mockHistory);

        ServiceResponse<List<WeatherHistory>> response = weatherAppService.getHistory(postalCode, user);

        assertNotNull(response);
        assertEquals(mockHistory, response.getResponse());
    }

    @Test
    void testGetHistory_ByUserOnly() {
        String user = "JohnDoe";
        List<WeatherHistory> mockHistory = new ArrayList<>();

        when(weatherHistoryDao.findByUser(user)).thenReturn(mockHistory);

        ServiceResponse<List<WeatherHistory>> response = weatherAppService.getHistory(null, user);

        assertNotNull(response);
        assertEquals(mockHistory, response.getResponse());
    }

    @Test
    void testGetHistory_ByPostalCodeOnly() {
        String postalCode = "33601";
        List<WeatherHistory> mockHistory = new ArrayList<>();

        when(weatherHistoryDao.findByPostalcode(postalCode)).thenReturn(mockHistory);

        ServiceResponse<List<WeatherHistory>> response = weatherAppService.getHistory(postalCode, null);

        assertNotNull(response);
        assertEquals(mockHistory, response.getResponse());
    }
}
