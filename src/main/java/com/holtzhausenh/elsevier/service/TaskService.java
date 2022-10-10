package com.holtzhausenh.elsevier.service;

import com.holtzhausenh.elsevier.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto addTask(TaskDto task);
    List<TaskDto> getTasks();

    TaskDto updateTask(Long id, TaskDto task);
}
