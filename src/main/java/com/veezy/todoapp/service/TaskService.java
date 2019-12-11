package com.veezy.todoapp.service;

import com.veezy.todoapp.model.Task;

import java.util.List;

public interface TaskService {
    public Task addTask(Task theTask);
    public List<Task> getAllTasks();
}
