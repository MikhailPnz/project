package ru.burenkov.weatherBroker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.dto.req.City;
import ru.burenkov.weatherBroker.entity.WeatherEntity;
import ru.burenkov.weatherBroker.exception.BusinessException;
import ru.burenkov.weatherBroker.mq.MqSender;
import ru.burenkov.weatherBroker.repository.WeatherRepositories;
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
    private final MqSender mqSender;

    public List<WeatherEntity> response(String in) throws BusinessException {
        List<WeatherEntity> weatherEntities = weatherRepositories.findAllByName(in);
        if (CollectionUtils.isNotEmpty(weatherEntities)) {
            return weatherEntities;
        } else {
            throw new BusinessException("the object was not found!");
        }
    }

    public String receiverMqReceiver(String in) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        City person = mapper.readValue(in, City.class);
        WeatherDto dto = mappingUtils.CityMapToWeatherDto(person);
        saveAllToBD(dto);
        ObjectMapper RecMap = new ObjectMapper();
        return RecMap.writeValueAsString(dto);
    }

    public void sendMqSender(String city) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        City page = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=" +city+ "&appid=28faf092d43ea66bbf585993c042bbe2", City.class);
        ObjectMapper mapper = new ObjectMapper();
        mqSender.send(mapper.writeValueAsString(page));
    }

    public void saveAll(List<WeatherEntity> weatherEntities) {
        weatherRepositories.saveAll(weatherEntities);
    }

    public void delete(String city) {
        WeatherEntity weatherDB = weatherRepositories.findByName(city);
        weatherRepositories.delete(weatherDB);
    }

    @Transactional
    public void saveAllToBD(WeatherDto dto) {
        List<WeatherEntity> weather = new ArrayList<>(
            List.of(
                    new WeatherEntity()
                            .setName(dto.getName())
                            .setTemp(dto.getTemp())
            ));
        saveAll(weather);
    }

}
