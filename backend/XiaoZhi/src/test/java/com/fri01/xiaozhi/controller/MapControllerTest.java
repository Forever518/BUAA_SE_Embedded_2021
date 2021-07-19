package com.fri01.xiaozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fri01.xiaozhi.common.database.TableMapColumn;
import com.fri01.xiaozhi.entity.Map;
import com.fri01.xiaozhi.service.MapService;
import org.junit.Before;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MapControllerTest {

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
    @Transactional
    void removeAll1() throws Exception {
        Map map = new Map();
        map.setMapName("kitchen");
        map.setMapShowPath("/home/robot/map_show_home");
        map.setIndexPath("/home/robot/index_home");
        map.setMapPath("/home/robot/map_home");
        mapService.save(map);

        mockMvc.perform(MockMvcRequestBuilders.post("/map/remove-all")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));

        List<Map> list = mapService.list();
        assertTrue(list.isEmpty());
    }

    @Test
    @Transactional
    void removeAll2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/map/remove-all")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));

        List<Map> list = mapService.list();
        assertTrue(list.isEmpty());
    }

    @Test
    void displayAll1() {
    }

    @Test
    @Transactional
    void insert1() throws Exception {
        QueryWrapper<Map> wrapper = new QueryWrapper<>();
        wrapper.eq(TableMapColumn.NAME.value(), "kitchen");
        mapService.remove(wrapper);

        String contentJson = "{\"name\":\"kitchen\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/map/insert")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(contentJson.getBytes())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));

    }
}