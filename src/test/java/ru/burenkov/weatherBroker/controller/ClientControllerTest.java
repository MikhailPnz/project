package ru.burenkov.weatherBroker.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.burenkov.weatherBroker.dto.UserCity;
import ru.burenkov.weatherBroker.dto.WeatherDto;
import ru.burenkov.weatherBroker.repository.WeatherRepositories;
import ru.burenkov.weatherBroker.service.WeatherService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    final String CITY_REQUEST = "Kuznetsk";

    @Autowired
    private WeatherRepositories weatherRepositories;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMVC;

    @Test
    public void returnsTheBodyAndTheOkStatus() throws Exception {

        UserCity city = new UserCity(CITY_REQUEST);

        mockMVC.perform(
                        post("/weather")
                                .content(objectMapper.writeValueAsString(city))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("City: " + CITY_REQUEST)));

        weatherService.delete(CITY_REQUEST);
    }

    @Test
    public void writesAndReadsAndDeletesAnObjectFromTheDatabase() throws Exception {

        WeatherDto weatherDTO = new WeatherDto();
        weatherDTO.setName(CITY_REQUEST);
        weatherDTO.setTemp(29.6);
        weatherService.saveAllToBD(weatherDTO);

        this.mockMVC.perform(get("/weather?city=" + CITY_REQUEST))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value(CITY_REQUEST))
                .andExpect(jsonPath("[0].temp").value(29.6));

        weatherService.delete(CITY_REQUEST);
    }

    @Test
    public void testException() throws Exception {
        this.mockMVC.perform(get("/weather?city=" + CITY_REQUEST))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("the object was not found!")));
    }
    
//    @Test
//    public void del() throws Exception {
//        weatherRepositories.deleteAll();
//    }

}