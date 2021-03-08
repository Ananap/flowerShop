package com.flowershop.service.impl;

import com.flowershop.model.Basket;
import com.flowershop.model.User;
import com.flowershop.model.UserInfo;
import com.flowershop.model.security.PasswordToken;
import com.flowershop.model.security.Role;
import com.flowershop.repository.*;
import com.flowershop.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BasketRepository basketRepository;

    public PasswordToken getPasswordToken(String token){
        return passwordTokenRepository.findByToken(token);
    }

    public void createPasswordTokenForUser(User user, String token){
        PasswordToken myToken = new PasswordToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(PasswordToken.calculateExpiryDate());
        passwordTokenRepository.save(myToken);
    }

    public User createUser (String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
        Role role = new Role("USER");
        role.setUser(user);
        roleRepository.save(role);
        Basket basket = new Basket();
        basket.setUser(user);
        basketRepository.save(basket);
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
        userRepository.save(user);
        return user;
    }

    public User findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser (User user) {
        userRepository.save(user);
    }

    public String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(12).toUpperCase();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exist"));
        return User.fromUser(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}