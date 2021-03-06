package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    User findByToken(String token);
}
