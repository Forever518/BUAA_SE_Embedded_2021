package com.fri01.xiaozhi.controller;

import com.fri01.xiaozhi.service.MapService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RosIndexControllerTest {

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
    void mark() {
    }

    @Test
    void save() {
    }

    @Test
    void count() {
    }

    @Test
    void renameMark() {
    }

    @Test
    void finishMark() {
    }
}