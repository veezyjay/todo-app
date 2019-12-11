package com.veezy.todoapp.service;

import com.veezy.todoapp.model.Task;

import java.util.List;

public interface TaskService {
    Task addTask(Task theTask);
    List<Task> getAllTasks();
    Task getTask(Integer taskId);
}
