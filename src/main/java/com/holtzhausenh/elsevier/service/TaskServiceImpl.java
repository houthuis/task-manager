package com.holtzhausenh.elsevier.service;

import com.holtzhausenh.elsevier.dto.TaskDto;
import com.holtzhausenh.elsevier.exception.TaskException;
import com.holtzhausenh.elsevier.persistence.entity.Task;
import com.holtzhausenh.elsevier.persistence.repository.TasksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TasksRepository tasksRepository;

    @Override
    public TaskDto addTask(TaskDto taskDto) {
        log.info("Entered addTask with dto: {}", taskDto);

        Task task = mapToEntity(taskDto);

        log.trace("Saving entity to db: {}", task);
        Task saved = tasksRepository.save(task);
        return mapToDto(saved);

//        Optional<Task> savedTask = tasksRepository.findByTitle(taskDto.getTitle());
//
//        if (savedTask.isPresent()) {
//            return mapToDto(savedTask.get());
//        } else {
//            throw new TaskException("Unable to save task");
//        }
    }

    @Override
    public List<TaskDto> getTasks() {
        log.info("Entered getTasks");

        List<Task> tasks = tasksRepository.findAll();
        log.debug("Found {} tasks", tasks.size());
        return tasks.stream().map(TaskServiceImpl::mapToDto).collect(Collectors.toList());
    }

    private static Task mapToEntity(TaskDto task) {
        Task entity = new Task();
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setFinished(task.isFinished());

        return entity;
    }

    private static TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .finished(task.isFinished())
                .build();
    }
}
