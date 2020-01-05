package com.veezy.todoapp.controller;

import com.veezy.todoapp.model.Status;
import com.veezy.todoapp.model.Task;
import com.veezy.todoapp.response.ResponseTemplate;
import com.veezy.todoapp.service.TaskService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Adds a new task for a user")
    public ResponseEntity<ResponseTemplate<Task>> addTask(@RequestBody Task newTask) {
        Task theTask = taskService.addTask(newTask);
        ResponseTemplate<Task> responseBody = new ResponseTemplate<>(HttpStatus.CREATED.value(),
                "Successfully added new task", theTask);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation("Returns a list of all tasks belonging to a user")
    public ResponseEntity<ResponseTemplate<List<Task>>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        ResponseTemplate<List<Task>> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved all tasks", tasks);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    @ApiOperation("Fetches a user's task based on the task id passed in")
    public ResponseEntity<ResponseTemplate<Task>> getTask(@PathVariable Integer taskId) {
        Task theTask = taskService.getTask(taskId);
        ResponseTemplate<Task> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved task", theTask);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/filter")
    @ApiOperation("Returns a user's tasks based on the status being passed in")
    public ResponseEntity<ResponseTemplate<List<Task>>> getTaskByStatus(@RequestParam(name = "status") Status status) {
        List<Task> tasks = taskService.getByStatus(status);

        ResponseTemplate<List<Task>> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved the tasks", tasks);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @ApiOperation("Deletes a single task")
    public ResponseEntity<String> deleteTask(@PathVariable("taskId") Integer taskId) {
        String responseBody = taskService.deleteTask(taskId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    @ApiOperation("Updates a single task")
    public ResponseEntity<ResponseTemplate<Task>> updateTask(@PathVariable Integer taskId, @RequestBody Task newTask) {
        Task theTask = taskService.updateTask(newTask, taskId);
        ResponseTemplate<Task> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully updated task", theTask);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
