package com.veezy.todoapp.service;

import com.veezy.todoapp.model.Task;
import com.veezy.todoapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTask(Task theTask) {
        return taskRepository.save(theTask);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTask(Integer taskId) {
        Optional<Task> theTask = taskRepository.findById(taskId);
        Task task = null;
        if (theTask.isPresent()) {
            task = theTask.get();
        }
        return task;
    }
}
