package ru.burenkov.weatherBroker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    Integer id;
    String name;
    Double temp;

}
