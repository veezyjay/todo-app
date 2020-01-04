package com.veezy.todoapp.service;

import com.veezy.todoapp.authentication.AuthenticationFacade;
import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.model.Status;
import com.veezy.todoapp.model.Task;
import com.veezy.todoapp.model.User;
import com.veezy.todoapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private UserService userService;
    private TaskRepository taskRepository;
    private AuthenticationFacade authenticationFacade;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService,
                           AuthenticationFacade authenticationFacade) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public Task addTask(Task theTask) {
        User user = userService.getUser(authenticationFacade.getId());
        theTask.setTaskStatus(Status.pending);
        theTask.setTaskCreator(user);
        return taskRepository.save(theTask);
    }

    @Override
    public List<Task> getAllTasks() {
        User user = userService.getUser(authenticationFacade.getId());
        return user.getTasks();
    }

    @Override
    public Task getTask(Integer taskId) {
        return taskRepository.findByIdAndTaskCreatorId(taskId, authenticationFacade.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    public String deleteTask(Integer taskId) {
        Optional<Task> taskOptional = taskRepository.findByIdAndTaskCreatorId(taskId, authenticationFacade.getId());
        if (taskOptional.isEmpty()) {
            throw new ResourceNotFoundException("Task not found");
        } else {
            taskRepository.delete(taskOptional.get());
        }
        return "Successfully deleted task with id - " + taskId;
    }

    @Override
    public Task updateTask(Task task, Integer taskId) {
        Optional<Task> taskOptional = taskRepository.findByIdAndTaskCreatorId(taskId, authenticationFacade.getId());
        if (taskOptional.isPresent()) {
            Task theTask = taskOptional.get();
            task.setId(theTask.getId());
            task.setTaskCreator(theTask.getTaskCreator());
            task.setCreatedAt(theTask.getCreatedAt());
            if (task.getTaskStatus().equals(Status.done)) {
                task.setCompletedAt(LocalDateTime.now());
            }
        } else {
            throw new ResourceNotFoundException("Task not found");
        }
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getByStatus(Status status) {
        return taskRepository.findByTaskStatusAndTaskCreatorId(status, authenticationFacade.getId());
    }
}
