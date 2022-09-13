package com.holtzhausenh.elsevier.persistence.repository;

import com.holtzhausenh.elsevier.persistence.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);
}
