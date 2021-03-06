package ru.burenkov.weatherBroker.utils;

import org.springframework.stereotype.Component;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.entity.WeatherEntity;
import ru.burenkov.weatherBroker.dto.req.City;

@Component
public class MappingUtils {

    //из entity в dto
    public WeatherDto mapToWeatherDto(WeatherEntity entity){
        WeatherDto dto = new WeatherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTemp(entity.getTemp());
        return dto;
    }
    //из dto в entity
    public WeatherEntity mapToWeatherEntity(WeatherDto dto){
        WeatherEntity entity = new WeatherEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setTemp(dto.getTemp());
        return entity;
    }
    //из city в dto
    public WeatherDto CityMapToWeatherDto(City city){
        WeatherDto dto = new WeatherDto();
        dto.setName(city.getName());
        dto.setTemp(city.getMain().getTemp());
        return dto;
    }
}
