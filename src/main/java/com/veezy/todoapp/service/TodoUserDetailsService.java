package com.veezy.todoapp.service;

import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.model.TodoUserDetails;
import com.veezy.todoapp.model.User;
import com.veezy.todoapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TodoUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public TodoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User theUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Incorrect username or password"));
        return new TodoUserDetails(theUser);
    }
}
