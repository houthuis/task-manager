package com.holtzhausenh.elsevier.controller;

import com.holtzhausenh.elsevier.dto.TaskDto;
import com.holtzhausenh.elsevier.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/task", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TaskManagerController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto addTask(@Valid @RequestBody TaskDto task) {
        return taskService.addTask(task);
    }

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getTasks();
    }
}
