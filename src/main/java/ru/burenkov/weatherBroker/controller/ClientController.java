package ru.burenkov.weatherBroker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.entities.WeatherEntity;
import ru.burenkov.weatherBroker.mq.mqSender;
import ru.burenkov.weatherBroker.repositories.WeatherRepositories;
import ru.burenkov.weatherBroker.services.WeatherService;
import ru.burenkov.weatherBroker.utils.MappingUtils;

import java.util.List;

@RestController
public class ClientController {

    private final WeatherService weatherService;
    private final WeatherRepositories weatherRepositories;

    @Autowired
    public ClientController(WeatherService weatherService, WeatherRepositories weatherRepositories) {
        this.weatherService = weatherService;
        this.weatherRepositories = weatherRepositories;
    }

    @Autowired
    private mqSender mqSender;

    @Autowired
    private MappingUtils mappingUtils;



    @PostMapping ("/weather")
    @ResponseBody
    public ResponseEntity<?>weather(@RequestParam String city) throws JsonProcessingException {
        mqSender.send(city);
        return new ResponseEntity<>("City: " + city, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam String city) {
        List<WeatherEntity> weatherEntities = weatherRepositories.findAllByName(city);
        return weatherEntities != null &&  !weatherEntities.isEmpty()
                ? new ResponseEntity<>(weatherEntities, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);



    }

}
