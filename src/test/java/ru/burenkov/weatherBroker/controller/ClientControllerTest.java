package ru.burenkov.weatherBroker.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @Autowired
    private ClientController controller;

    @Test
    public void theRequestReturnsTheParameterNameAndTheStatus200() throws Exception {
        this.mockMVC.perform(post("/weather?city=Moscow"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("City: Moscow")));
    }

    @Test
    public void test() throws Exception {
        this.mockMVC.perform(get("/weather?city=Moscow"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("City: Moscow")));
    }


}