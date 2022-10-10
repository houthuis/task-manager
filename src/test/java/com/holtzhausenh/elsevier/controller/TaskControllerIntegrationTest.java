package com.holtzhausenh.elsevier.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holtzhausenh.elsevier.dto.TaskDto;
import com.holtzhausenh.elsevier.persistence.entity.Task;
import com.holtzhausenh.elsevier.persistence.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TasksRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testAddTask_whenPostTask_thenStatus200() throws Exception {
        TaskDto taskDto = getTaskDto();

        MvcResult mvcResult = mvc.perform(post("/api/task")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("PostTest"))
                .andExpect(jsonPath("$.description").value("Post Test"))
                .andExpect(jsonPath("$.finished").value("false"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        TaskDto actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TaskDto.class);

        assertEquals(taskDto, actualResponse);
    }

    @Test
    void testGetTasks_whenGetTasks_thenStatus200() throws Exception {

        createTestTask();

        mvc.perform(get("/api/task")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$[3].title").value("TestTitle"))
                .andExpect(jsonPath("$[3].description").value("Test Description"))
                .andExpect(jsonPath("$[3].finished").value("false"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testAddTask_whenInvalidTask_expectBadRequest() throws Exception {

        TaskDto task = getTaskDto();
        task.setTitle("");

        mvc.perform(post("/api/task")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is mandatory"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testUpdateTask_whenValidTask_expect200() throws Exception {

        TaskDto taskDto = getTaskDto();
        taskDto.setTitle("NewTitle");

        mvc.perform(put("/api/task/1")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NewTitle"))
                .andDo(MockMvcResultHandlers.print());

    }

    public TaskDto getTaskDto() {
        return TaskDto.builder()
                .title("PostTest")
                .description("Post Test")
                .finished(false)
                .build();
    }

    public void createTestTask() {

        Task task = new Task();
        task.setTitle("TestTitle");
        task.setDescription("Test Description");
        task.setFinished(false);

        repository.save(task);
    }
}
