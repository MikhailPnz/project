package ru.burenkov.weatherBroker.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.req.City;
import ru.burenkov.weatherBroker.services.WeatherService;
import ru.burenkov.weatherBroker.utils.MappingUtils;

//@RequiredArgsConstructor // как-то надо его использовать
@Slf4j
public class MqReceiver {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private MappingUtils mappingUtils;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive(String in) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        City person = mapper.readValue(in, City.class);
        WeatherDto dto = mappingUtils.CityMapToWeatherDto(person);
        weatherService.saveAllToBD(dto);
        ObjectMapper RecMap = new ObjectMapper();
        String json = RecMap.writeValueAsString(dto);
        log.info("[x] Received: " + json);
    }

}
