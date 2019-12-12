package com.veezy.todoapp.service;

import com.veezy.todoapp.model.Task;
import com.veezy.todoapp.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    Task returnTask;

    @BeforeEach
    void setUp() {
        returnTask = Task.builder().id(1).createdAt(LocalDateTime.now()).build();
    }

    @Test
    void addTask() {
        Task taskToAdd = Task.builder().id(1).build();
        when(taskRepository.save(any())).thenReturn(returnTask);
        Task addedTask = taskService.addTask(taskToAdd);
        assertNotNull(addedTask);
    }

    @Test
    void getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(returnTask);

        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> testTasks = taskService.getAllTasks();
        assertNotNull(testTasks);
        assertEquals(1, testTasks.size());
    }

    @Test
    void getTask() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(returnTask));
        Task testTask = taskService.getTask(1);
        assertNotNull(testTask);
        assertEquals(1, testTask.getId());
    }

    @Test
    void getTaskNotFound() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.empty());
        Task testTask = taskService.getTask(5);
        assertNull(testTask);
    }

    @Test
    void deleteTask() {
        taskService.deleteTask(1);
        verify(taskRepository).deleteById(anyInt());
    }

    @Test
    void updateTask() {
        when(taskRepository.save(any())).thenReturn(returnTask);
        Task updatedTask = taskService.updateTask(returnTask);
        assertEquals(1, updatedTask.getId());
        assertEquals(returnTask.getCreatedAt(), updatedTask.getCreatedAt());
        verify(taskRepository).findById(anyInt());
    }
}