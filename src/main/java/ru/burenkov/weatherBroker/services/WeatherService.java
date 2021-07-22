package ru.burenkov.weatherBroker.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.entities.WeatherEntity;
import ru.burenkov.weatherBroker.repositories.WeatherRepositories;
import ru.burenkov.weatherBroker.utils.MappingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepositories weatherRepositories;
    private final MappingUtils mappingUtils;

    public void saveAll(List<WeatherEntity> weatherEntities) {
        weatherRepositories.saveAll(weatherEntities);
    }

    @Transactional
    public void saveAllToBD(WeatherDto dto) {
        List<WeatherEntity> weather = new ArrayList<>(
                Arrays.asList(
                        new WeatherEntity()
                                .setName(dto.getName())
                                .setTemp(dto.getTemp())
                ));
        saveAll(weather);
    }

    //для листа продуктов мы использовали стрим
    public List<WeatherDto> findAll() {
        return weatherRepositories.findAll().stream() //создали из листа стирим
                .map(mappingUtils::mapToWeatherDto) //оператором из streamAPI map, использовали для каждого элемента метод mapToProductDto из класса MappingUtils
                .collect(Collectors.toList()); //превратили стрим обратно в коллекцию, а точнее в лист
    }

    //для листа продуктов мы использовали стрим
    public List<WeatherDto> find(String name) {
        return weatherRepositories.findAllByName(name).stream() //создали из листа стирим
                .map(mappingUtils::mapToWeatherDto) //оператором из streamAPI map, использовали для каждого элемента метод mapToProductDto из класса MappingUtils
                .collect(Collectors.toList()); //превратили стрим обратно в коллекцию, а точнее в лист
    }


    //для одиночного продукта обошлись проще
    /*public WeatherDto findById(Integer id) {
        return mappingUtils.mapToWeatherDto( //в метод mapToProductDto
                weatherRepositories.findById(id) //поместили результат поиска по id
                        .orElse(new WeatherEntity()) //если ни чего не нашли, то вернем пустой entity
        );
    }*/

}
