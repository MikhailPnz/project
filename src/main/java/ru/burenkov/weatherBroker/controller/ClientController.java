package ru.burenkov.weatherBroker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.burenkov.weatherBroker.entities.WeatherEntity;
import ru.burenkov.weatherBroker.mq.MqSender;
import ru.burenkov.weatherBroker.repositories.WeatherRepositories;

import java.util.List;

@RestController
public class ClientController {

    private final WeatherRepositories weatherRepositories;
    private final MqSender mqSender;

    @Autowired
    public ClientController(WeatherRepositories weatherRepositories, MqSender mqSender) {
        this.weatherRepositories = weatherRepositories;
        this.mqSender = mqSender;
    }

    @PostMapping ("/weather")
    @ResponseBody
    public ResponseEntity<?>weather(@RequestParam String city) throws JsonProcessingException {
        mqSender.send(city); // это бизнес-логика убрать в сервис
        return new ResponseEntity<>("City: " + city, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam String city) {
        List<WeatherEntity> weatherEntities = weatherRepositories.findAllByName(city);
        return weatherEntities != null &&  !weatherEntities.isEmpty()
                ? new ResponseEntity<>(weatherEntities, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND); // перенести в слой сервиса,
                                                                // делать в сервисе проверку: если запись не найдена, то бросать своё кастомное исключение,
                                                                // для обработки исключений можно написать свой @ControllerAdvice, который будет в @ExceptionHandler
                                                                // отлавливать исключения и каким-то образом их обрабатывать и выдавать информацию пользовател
    }

    @PostMapping ("/ok")
    @ResponseBody
    public ResponseEntity<?>weather() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
