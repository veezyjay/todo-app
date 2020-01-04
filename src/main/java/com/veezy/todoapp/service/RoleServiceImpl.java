package com.veezy.todoapp.service;

import com.veezy.todoapp.exception.ResourceNotFoundException;
import com.veezy.todoapp.model.Role;
import com.veezy.todoapp.repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRole(Integer roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role does not exist"));
    }
}
