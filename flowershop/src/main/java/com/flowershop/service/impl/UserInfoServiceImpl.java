package com.flowershop.service.impl;

import com.flowershop.model.User;
import com.flowershop.model.UserInfo;
import com.flowershop.repository.UserInfoRepository;
import com.flowershop.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfo findByUser (User user) {
        return userInfoRepository.findByUser(user);
    }

    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
