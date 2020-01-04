package com.veezy.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.model.Task;
import com.veezy.todoapp.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    TaskService taskService;

    @InjectMocks
    TaskController taskController;

    MockMvc mockMvc;

    List<Task> tasks;
    Task task1;

    @BeforeEach
    void setUp() {
        tasks = new ArrayList<>();
        task1 = Task.builder().id(1).build();
        tasks.add(task1);
        tasks.add(Task.builder().id(2).build());

        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void addTask() throws Exception {
        Task taskToAdd = Task.builder().title("my task").build();
        when(taskService.addTask(any())).thenReturn(taskToAdd);
        mockMvc.perform(post("/tasks")
                .content(asJsonString(taskToAdd))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").exists());
    }

    @Test
    void getAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(tasks);
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getTask() throws Exception {
        when(taskService.getTask(anyInt())).thenReturn(task1);
        mockMvc.perform(get("/tasks/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void getTaskWrongId() throws Exception {
        when(taskService.getTask(anyInt())).thenReturn(null);
        try {
            mockMvc.perform(get("/tasks/{id}", 1));
        } catch (NestedServletException e) {
            assertEquals(ResourceNotFoundException.class, e.getRootCause().getClass());
        }
    }

    @Test
    void deleteTask() throws Exception {
        when(taskService.deleteTask(anyInt())).thenReturn("");
        mockMvc.perform(delete("/tasks/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void updateTask() throws Exception {
        Task taskToUpdate = Task.builder().id(1).title("updated task").build();
        when(taskService.updateTask(any(), anyInt())).thenReturn(taskToUpdate);
        mockMvc.perform(put("/tasks/{taskId}", 1)
                .content(asJsonString(taskToUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("updated task"));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}