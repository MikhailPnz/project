package ru.burenkov.weatherBroker.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.req.City;
import ru.burenkov.weatherBroker.req.Main;

public class mqReceiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive(String in) throws InterruptedException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        WeatherDto person = mapper.readValue(in, WeatherDto.class);
        System.out.println(" [x] Received '" + person + "'");
    }
/*
    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }*/
/*
    public void receive(String in) throws InterruptedException, JsonProcessingException {
        //StopWatch watch = new StopWatch();
        //watch.start();
        ObjectMapper mapper = new ObjectMapper();
        //City person = mapper.readValue(in, City.class);
        WeatherDto person = mapper.readValue(in, WeatherDto.class);
        System.out.println(" [x] Received '" + person + "'");
        //doWork(in);
        //watch.stop();
        //System.out.println("instance " + receiver + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }*/
/*
    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }*/
}
