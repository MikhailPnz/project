package ru.burenkov.weatherBroker.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.burenkov.weatherBroker.dto.req.City;

@Slf4j
public class MqSender {

    @Autowired
    private RabbitTemplate template; //шаблон

    @Autowired
    private FanoutExchange fanout; //разветвление

    public void send(String json) {
        template.convertAndSend(fanout.getName(), "", json);
        log.info("[x] Sent: " + json);
    }

}
