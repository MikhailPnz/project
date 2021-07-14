package ru.burenkov.weatherBroker.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.burenkov.weatherBroker.req.City;

public class mqSender {

    @Autowired
    private RabbitTemplate template; //шаблон

    @Autowired
    private FanoutExchange fanout; //разветвление

    //@Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send(String city) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        City page = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=" +city+ "&appid=28faf092d43ea66bbf585993c042bbe2", City.class);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(page);
        template.convertAndSend(fanout.getName(), "", json);
        System.out.println(" [x] Sent '" + json + "'");
    }

}
