package ru.burenkov.weatherBroker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.burenkov.weatherBroker.dto.UserCity;
import ru.burenkov.weatherBroker.exception.BusinessException;
import ru.burenkov.weatherBroker.service.WeatherService;

@RestController
public class ClientController {

    final static Logger logger = Logger.getLogger(ClientController.class);

    private final WeatherService weatherService;

    @Autowired
    public ClientController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping ("/weather")
    public ResponseEntity<?> weather(@RequestBody UserCity city) throws JsonProcessingException {
        weatherService.sendMqSender(city.getCity());
        logger.info("POST запрос: " + city.getCity());
        return new ResponseEntity<>("City: " + city.getCity(), HttpStatus.OK);
    }

    @GetMapping(value = "/weather")
    public ResponseEntity<?> filtertControllerAdvice(@RequestParam String city) throws BusinessException {
        return new ResponseEntity<>(weatherService.response(city), HttpStatus.OK);
    }

}
