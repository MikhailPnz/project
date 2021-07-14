package ru.burenkov.weatherBroker.utils;

import org.springframework.stereotype.Service;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.entities.WeatherEntity;
import ru.burenkov.weatherBroker.req.City;
import ru.burenkov.weatherBroker.req.Main;

@Service
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
}
