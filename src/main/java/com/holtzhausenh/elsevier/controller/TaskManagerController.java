package com.holtzhausenh.elsevier.controller;

import com.holtzhausenh.elsevier.dto.TaskDto;
import com.holtzhausenh.elsevier.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/task")
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

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable(name = "id") Long id, @Valid @RequestBody TaskDto task) {
        return taskService.updateTask(id, task);
    }
}
