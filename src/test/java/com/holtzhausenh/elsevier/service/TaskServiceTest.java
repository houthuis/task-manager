package com.holtzhausenh.elsevier.service;

import com.holtzhausenh.elsevier.dto.TaskDto;
import com.holtzhausenh.elsevier.exception.TaskException;
import com.holtzhausenh.elsevier.persistence.entity.Task;
import com.holtzhausenh.elsevier.persistence.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void testUpdateTask_ExpectSuccess() {

        TaskDto task = TaskDto.builder()
                .title("NewTitle")
                .description("New desc")
                .finished(true)
                .build();

        Task savedEntity = new Task();
        savedEntity.setTitle("oldTitle");
        savedEntity.setDescription("Old Desc");
        savedEntity.setFinished(false);

        Task toUpdate = new Task();
        toUpdate.setTitle(task.getTitle());
        toUpdate.setDescription(task.getDescription());
        toUpdate.setFinished(task.isFinished());

        when(repository.findById(1L)).thenReturn(Optional.of(toUpdate));
        when(repository.save(toUpdate)).thenReturn(toUpdate);

        TaskDto actualTask = taskService.updateTask(1L, task);

        assertEquals(task.getTitle(), actualTask.getTitle());
        assertEquals(task.getDescription(), actualTask.getDescription());
        assertEquals(task.isFinished(), actualTask.isFinished());
    }

    @Test
    void testUpdateTask_whenNotFound_expectException() {

        TaskDto task = TaskDto.builder()
                .title("NewTitle")
                .description("New desc")
                .finished(true)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                TaskException.class,
                () -> taskService.updateTask(1L, task)
        );
    }
}
