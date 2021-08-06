package ru.burenkov.weatherBroker.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.dto.req.City;
import ru.burenkov.weatherBroker.service.WeatherService;
import ru.burenkov.weatherBroker.utils.MappingUtils;

/**
 //@RequiredArgsConstructor // как-то надо его использовать
 */

@Slf4j
public class MqReceiver {

    @Autowired
    private WeatherService weatherService;

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(String in) throws JsonProcessingException {
        log.info("[x] Received: " + weatherService.receiverMqReceiver(in));
    }

}
