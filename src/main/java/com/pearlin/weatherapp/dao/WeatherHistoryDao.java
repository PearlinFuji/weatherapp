/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.dao;

import com.pearlin.weatherapp.domain.WeatherHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author pearlin
 */
public interface WeatherHistoryDao extends JpaRepository<WeatherHistory, Integer> {
    
    @Query("SELECT w FROM WeatherHistory w WHERE w.user = :user")
    List<WeatherHistory> findByUser(@Param("user") String user);

    @Query(value = "SELECT w FROM WeatherHistory w WHERE w.postalcode = :postalcode")
    List<WeatherHistory> findByPostalcode(@Param("postalcode") String postalcode);
    
    @Query(value = "SELECT w FROM WeatherHistory w WHERE w.postalcode = :postalcode AND w.user = :user")
    List<WeatherHistory> findBy(@Param("postalcode") String postalcode, @Param("user") String user);
}
