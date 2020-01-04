package com.veezy.todoapp.controller;

import com.veezy.todoapp.model.User;
import com.veezy.todoapp.request.AdminRequest;
import com.veezy.todoapp.request.SignUpRequest;
import com.veezy.todoapp.response.ResponseTemplate;
import com.veezy.todoapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseTemplate<User>> addUser(@Valid @RequestBody SignUpRequest newUserRequest) {
        User theUser = userService.addUser(newUserRequest);
        ResponseTemplate<User> responseBody = new ResponseTemplate<>(HttpStatus.CREATED.value(),
                "Successfully added new user", theUser);
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/registered-users")
    public ResponseEntity<ResponseTemplate<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ResponseTemplate<List<User>> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved all registered users", users);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/registered-users/{userId}")
    public ResponseEntity<ResponseTemplate<User>> getUser(@PathVariable Integer userId) {
        User theUser = userService.getUser(userId);
        ResponseTemplate<User> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "Successfully retrieved user", theUser);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/registered-users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        String responseBody = userService.deleteUser(userId);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PatchMapping("/registered-users")
    public ResponseEntity<ResponseTemplate<User>> makeUserAnAdmin(@Valid @RequestBody AdminRequest adminRequest) {
        User theUser = userService.makeAdmin(adminRequest);
        ResponseTemplate<User> responseBody = new ResponseTemplate<>(HttpStatus.OK.value(),
                "User with " + theUser.getId() + " is now an admin", theUser);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
