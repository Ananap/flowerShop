package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.security.Role;

public interface RoleService {
    Role getByName(String name);
}
