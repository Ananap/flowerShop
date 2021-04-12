package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getByName(String name);
}
