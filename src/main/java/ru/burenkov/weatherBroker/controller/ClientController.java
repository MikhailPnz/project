package ru.burenkov.weatherBroker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.burenkov.weatherBroker.exception.BusinessException;
import ru.burenkov.weatherBroker.repository.WeatherRepositories;
import ru.burenkov.weatherBroker.service.WeatherService;

@RestController
public class ClientController {

    final static Logger logger = Logger.getLogger(ClientController.class);

    private final WeatherRepositories weatherRepositories;
    private final WeatherService weatherService;

    @Autowired
    public ClientController(WeatherRepositories weatherRepositories, WeatherService weatherService) {
        this.weatherRepositories = weatherRepositories;
        this.weatherService = weatherService;
    }

    @PostMapping ("/weather")
    @ResponseBody
    public ResponseEntity<?>weather(@RequestParam String city) throws JsonProcessingException {
        weatherService.sendMqSender(city);
        logger.info("POST запрос: " + city);
        return new ResponseEntity<>("City: " + city, HttpStatus.OK);
    }
/*
    @GetMapping("/weather")
    public ResponseEntity<?> filter(@RequestParam String city) {
        List<WeatherEntity> weatherEntities = weatherRepositories.findAllByName(city);
        return weatherEntities != null &&  !weatherEntities.isEmpty()
                ? new ResponseEntity<>(weatherEntities, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    @GetMapping(value = "/weather")
    public ResponseEntity<?> filtertControllerAdvice(@RequestParam String city) throws BusinessException {
        return new ResponseEntity<>(weatherService.response(city), HttpStatus.OK);
    }


}
