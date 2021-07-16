package ru.burenkov.weatherBroker.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.entities.WeatherEntity;
import ru.burenkov.weatherBroker.req.City;
import ru.burenkov.weatherBroker.services.WeatherService;
import ru.burenkov.weatherBroker.utils.MappingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@RequiredArgsConstructor
public class mqReceiver {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MappingUtils mappingUtils;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive(String in) throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        City person = mapper.readValue(in, City.class);
        WeatherDto dto = mappingUtils.CityMapToWeatherDto(person);
        List<WeatherEntity> weather = new ArrayList<>(
                Arrays.asList(
                        new WeatherEntity()
                                .setName(dto.getName())
                                .setTemp(dto.getTemp())
                ));
        weatherService.saveAll(weather);

        System.out.println(" [x] Received '" + dto.toString() + "'");

    }

}
