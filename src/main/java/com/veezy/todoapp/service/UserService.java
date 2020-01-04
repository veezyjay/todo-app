package com.veezy.todoapp.service;

import com.veezy.todoapp.model.User;
import com.veezy.todoapp.request.AdminRequest;
import com.veezy.todoapp.request.SignUpRequest;

import java.util.List;

public interface UserService {
    User getUser(Integer userId);
    List<User> getAllUsers();
    User addUser(SignUpRequest newUserRequest);
    String deleteUser(Integer userId);
    User makeAdmin(AdminRequest adminRequest);
}
