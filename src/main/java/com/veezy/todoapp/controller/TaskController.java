package com.veezy.todoapp.controller;

import com.veezy.todoapp.exception.TaskNotFoundException;
import com.veezy.todoapp.model.Status;
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
            throw new TaskNotFoundException("Task is not available. Try again with a valid task ID");
        }
        ResponseTemplate<Task> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved task", theTask);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseTemplate<List<Task>>> getTaskByStatus(@RequestParam(name = "status") Status status) {
        List<Task> tasks = taskService.getByStatus(status);

        ResponseTemplate<List<Task>> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved the tasks", tasks);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId) {
        String responseBody = taskService.deleteTask(taskId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseTemplate<Task>> updateTask(@RequestBody Task newTask) {
        Task theTask = taskService.updateTask(newTask);
        ResponseTemplate<Task> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully updated task", theTask);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
