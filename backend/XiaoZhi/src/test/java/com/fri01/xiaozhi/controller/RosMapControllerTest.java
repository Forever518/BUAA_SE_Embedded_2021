package com.fri01.xiaozhi.controller;

import com.fri01.xiaozhi.service.MapService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RosMapControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MapService mapService;

    private MockMvc mockMvc;
    private MockHttpSession mockHttpSession;

    @BeforeEach
    void SetUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockHttpSession = new MockHttpSession();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ros-map/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
    }

    @Test
    void save1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ros-map/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
        mockMvc.perform(MockMvcRequestBuilders.post("/ros-map/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
    }

    @Test
    void save2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ros-map/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.code").value("601"));
    }
}
