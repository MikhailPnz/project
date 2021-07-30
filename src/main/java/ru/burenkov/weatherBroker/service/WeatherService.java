package ru.burenkov.weatherBroker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
//@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepositories weatherRepositories;
    private final MappingUtils mappingUtils;
    private final MqSender mqSender;

    public WeatherService(WeatherRepositories weatherRepositories, MappingUtils mappingUtils, MqSender mqSender) {
        this.weatherRepositories = weatherRepositories;
        this.mappingUtils = mappingUtils;
        this.mqSender = mqSender;
    }

    public List<WeatherEntity> response(String in) throws BusinessException {
        List<WeatherEntity> weatherEntities = weatherRepositories.findAllByName(in);
        if (weatherEntities != null &&  !weatherEntities.isEmpty()) {
            return (weatherEntities);
        }
        else {
            throw new BusinessException("запись не найдена!");
        }
    }

    public String receiverMqReceiver(String in) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        City person = mapper.readValue(in, City.class);
        WeatherDto dto = mappingUtils.CityMapToWeatherDto(person);
        saveAllToBD(dto);
        ObjectMapper RecMap = new ObjectMapper();
        String json = RecMap.writeValueAsString(dto);
        return json; 
    }

    public void sendMqSender(String city) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        City page = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=" +city+ "&appid=28faf092d43ea66bbf585993c042bbe2", City.class);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(page);
        mqSender.send(json);
    }

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

    public List<WeatherDto> findAll() {
        return weatherRepositories.findAll().stream() //создали из листа стирим
                .map(mappingUtils::mapToWeatherDto) //оператором из streamAPI map, использовали для каждого элемента метод mapToProductDto из класса MappingUtils
                .collect(Collectors.toList()); //превратили стрим обратно в коллекцию, а точнее в лист
    }

    public List<WeatherDto> find(String name) {
        return weatherRepositories.findAllByName(name).stream() //создали из листа стирим
                .map(mappingUtils::mapToWeatherDto) //оператором из streamAPI map, использовали для каждого элемента метод mapToProductDto из класса MappingUtils
                .collect(Collectors.toList()); //превратили стрим обратно в коллекцию, а точнее в лист
    }

}
