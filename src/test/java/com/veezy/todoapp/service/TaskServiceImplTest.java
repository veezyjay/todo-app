package com.veezy.todoapp.service;

import com.veezy.todoapp.authentication.AuthenticationFacade;
import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.model.Status;
import com.veezy.todoapp.model.Task;
import com.veezy.todoapp.model.User;
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

    @Mock
    AuthenticationFacade authenticationFacade;

    @Mock
    UserService userService;

    @InjectMocks
    TaskServiceImpl taskService;

    Task returnTask;
    User user;

    @BeforeEach
    void setUp() {
        returnTask = Task.builder().id(1).createdAt(LocalDateTime.now()).taskStatus(Status.pending).build();
        user = User.builder().id(1).username("user").build();
    }

    @Test
    void addTask() {
        when(authenticationFacade.getId()).thenReturn(user.getId());
        when(userService.getUser(anyInt())).thenReturn(user);
        when(taskRepository.save(any())).thenReturn(returnTask);

        Task addedTask = taskService.addTask(returnTask);
        assertNotNull(addedTask);
        assertEquals(returnTask.getId(), addedTask.getId());
    }

    @Test
    void getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(returnTask);
        user.setTasks(taskList);

        when(authenticationFacade.getId()).thenReturn(1);
        when(userService.getUser(anyInt())).thenReturn(user);

        List<Task> testTasks = taskService.getAllTasks();
        verify(authenticationFacade).getId();
        assertNotNull(testTasks);
        assertEquals(user.getTasks().size(), testTasks.size());
    }

    @Test
    void getAlreadyExistingTask() {
        when(authenticationFacade.getId()).thenReturn(user.getId());
        when(taskRepository.findByIdAndTaskCreatorId(anyInt(), anyInt())).thenReturn(Optional.of(returnTask));
        Task testTask = taskService.getTask(1);
        assertNotNull(testTask);
        assertEquals(1, testTask.getId());
    }

    @Test
    void getNonExistingTask() {
        when(authenticationFacade.getId()).thenReturn(user.getId());
        when(taskRepository.findByIdAndTaskCreatorId(anyInt(), anyInt())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () ->taskService.getTask(5));
    }

    @Test
    void deleteTask() {
        when(authenticationFacade.getId()).thenReturn(user.getId());
        when(taskRepository.findByIdAndTaskCreatorId(anyInt(), anyInt())).thenReturn(Optional.of(returnTask));
        taskService.deleteTask(1);
        verify(taskRepository).delete(returnTask);
    }

    @Test
    void updateTask() {
        when(authenticationFacade.getId()).thenReturn(user.getId());
        when(taskRepository.findByIdAndTaskCreatorId(anyInt(), anyInt())).thenReturn(Optional.of(returnTask));
        when(taskRepository.save(any())).thenReturn(returnTask);

        Task updatedTask = taskService.updateTask(returnTask, 1);
        assertEquals(1, updatedTask.getId());
        assertEquals(returnTask.getCreatedAt(), updatedTask.getCreatedAt());
        assertEquals(returnTask.getTaskCreator(), updatedTask.getTaskCreator());
    }

    @Test
    void getByStatus() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(returnTask);

        when(authenticationFacade.getId()).thenReturn(user.getId());
        when(taskRepository.findByTaskStatusAndTaskCreatorId(any(Status.class), anyInt())).thenReturn(taskList);

        List<Task> testTasks = taskService.getByStatus(Status.pending);
        assertNotNull(testTasks);
        assertEquals(1, testTasks.size());
    }
}