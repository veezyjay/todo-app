package com.veezy.todoapp.controller;

import com.veezy.todoapp.model.Task;
import com.veezy.todoapp.response.ResponseTemplate;
import com.veezy.todoapp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate<Task>> addTask(@RequestBody Task newTask) {
        Task theTask = taskService.addTask(newTask);
        ResponseTemplate<Task> responseBody = new ResponseTemplate<>(HttpStatus.CREATED.value(),
                "Successfully added new task", theTask);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseTemplate<List<Task>>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        ResponseTemplate<List<Task>> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved all tasks", tasks);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseTemplate<Task>> getTask(@PathVariable Integer taskId) {
        Task theTask = taskService.getTask(taskId);
        if (theTask == null) {
            throw new RuntimeException("Task is not available. Try again");
        }
        ResponseTemplate<Task> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved task", theTask);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
