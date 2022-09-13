package com.holtzhausenh.elsevier.service;

import com.holtzhausenh.elsevier.dto.TaskDto;
import com.holtzhausenh.elsevier.persistence.entity.Task;
import com.holtzhausenh.elsevier.persistence.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    TaskServiceImpl taskService;

    @Mock
    TasksRepository repository;

    @Test
    void testAddTask_ExpectSuccess() {

        TaskDto task = TaskDto.builder()
                .title("Task1")
                .description("Task One")
                .finished(false)
                .build();

        Task entity = new Task();
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setFinished(task.isFinished());

        when(repository.save(entity)).thenReturn(entity);

        TaskDto actualTask = taskService.addTask(task);

        assertEquals(task.getTitle(), actualTask.getTitle());
        assertEquals(task.getDescription(), actualTask.getDescription());
        assertEquals(task.isFinished(), actualTask.isFinished());
    }

    @Test
    void testGetTasks_expectSuccess() {

        Task task1 = new Task();
        task1.setTitle("Task1");
        task1.setDescription("Task One");
        task1.setFinished(false);

        Task task2 = new Task();
        task2.setTitle("Task2");
        task2.setDescription("Task Two");
        task2.setFinished(false);

        when(repository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<TaskDto> actualTasks = taskService.getTasks();

        TaskDto actualTask1 = actualTasks.get(0);
        assertEquals(2, actualTasks.size());
        assertEquals(task1.getTitle(), actualTask1.getTitle());
        assertEquals(task1.getDescription(), actualTask1.getDescription());
        assertEquals(task1.isFinished(), actualTask1.isFinished());
    }
}
