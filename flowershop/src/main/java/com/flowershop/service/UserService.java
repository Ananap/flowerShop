package com.flowershop.service;

import com.flowershop.model.User;
import com.flowershop.model.security.PasswordToken;

public interface UserService {
    PasswordToken getPasswordToken(String token);
    void createPasswordTokenForUser(User user, String token);
    User createUser (String username, String email, String password);
    User findByEmail (String email);
    void saveUser (User user);
    User findByUsername(String username);
    String generateRandomPassword();
}
