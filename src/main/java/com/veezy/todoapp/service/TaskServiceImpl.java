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

    @Override
    public String deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
        return "Successfully deleted task with id - " + taskId;
    }

    @Override
    public Task updateTask(Task task) {
        Integer taskId = task.getId();
        if (taskId != null) {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            if (taskOptional.isPresent()) {
                Task theTask = taskOptional.get();
                task.setCreatedAt(theTask.getCreatedAt());
            }

        }
        return taskRepository.save(task);
    }
}
