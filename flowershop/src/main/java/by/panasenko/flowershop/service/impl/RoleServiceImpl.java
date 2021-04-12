package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.model.security.Role;
import by.panasenko.flowershop.repository.RoleRepository;
import by.panasenko.flowershop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(name);
    }
}
