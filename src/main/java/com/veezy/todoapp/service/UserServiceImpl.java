package com.veezy.todoapp.service;

import com.veezy.todoapp.exception.PasswordsDoNotMatchException;
import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.model.Role;
import com.veezy.todoapp.model.User;
import com.veezy.todoapp.repository.UserRepository;
import com.veezy.todoapp.request.AdminRequest;
import com.veezy.todoapp.request.SignUpRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;



    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User getUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with id - " + userId + "  not found");
        }
        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(SignUpRequest newUserRequest) {
        if (!newUserRequest.getPassword().equals(newUserRequest.getPasswordConfirm())) {
            throw new PasswordsDoNotMatchException("Passwords do not match");
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(newUserRequest.getPassword());
        User newUser = User.builder()
                .username(newUserRequest.getUsername()).password(hashedPassword)
                .status("active").build();

        Role userRole = roleService.getRole(1);
        newUser.addRole(userRole);
        return userRepository.save(newUser);
    }

    @Override
    public String deleteUser(Integer userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("No user with id " + userId + " exists");
        }
        return "Successfully deleted user: " + userId;
    }

    @Override
    public User makeAdmin(AdminRequest adminRequest) {
        Integer userId = adminRequest.getId();
        User theUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user with id " + userId + " exists"));
        Role adminRole = roleService.getRole(2);
        theUser.addRole(adminRole);
        return userRepository.save(theUser);
    }

}
