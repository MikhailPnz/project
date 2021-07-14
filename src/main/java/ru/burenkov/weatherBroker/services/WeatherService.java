package ru.burenkov.weatherBroker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.burenkov.weatherBroker.entities.WeatherEntity;
import ru.burenkov.weatherBroker.repositories.WeatherRepositories;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepositories weatherRepositories;

    public void saveAll(List<WeatherEntity> weatherEntities) {
        weatherRepositories.saveAll(weatherEntities);
    }



}
