package com.veezy.todoapp.repository;

import com.veezy.todoapp.model.Status;
import com.veezy.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByTaskStatusAndTaskCreatorId(Status taskStatus, Integer creatorId);
    Optional<Task> findByIdAndTaskCreatorId(Integer taskId, Integer creatorId);
}
