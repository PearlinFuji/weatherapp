/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pearlin.weatherapp.utils.CustomDateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author pearlin
 */
@Entity
@Table(name = "report")
@Getter
@Setter
public class WeatherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String user;

    private String postalcode;

    @Column(columnDefinition = "TEXT")
    @JsonIgnore
    private String weatherinfo;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date timestamp;

    @Transient
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @JsonProperty("weatherinfo")
    public JsonNode getWeatherinfoAsJson() {
        try {
            return objectMapper.readTree(weatherinfo); // Parse to JsonNode for JSON response
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weatherinfo JSON", e);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.postalcode);
        hash = 67 * hash + Objects.hashCode(this.weatherinfo);
        hash = 67 * hash + Objects.hashCode(this.timestamp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WeatherHistory other = (WeatherHistory) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.postalcode, other.postalcode)) {
            return false;
        }
        if (!Objects.equals(this.weatherinfo, other.weatherinfo)) {
            return false;
        }
        return Objects.equals(this.timestamp, other.timestamp);
    }

    @Override
    public String toString() {
        return "WeatherHistory{" + "id=" + id + ", user=" + user + ", postalcode=" + postalcode + ", weatherinfo=" + weatherinfo + ", timestamp=" + timestamp + '}';
    }

}
