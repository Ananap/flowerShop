package com.flowershop.service;

import com.flowershop.model.User;
import com.flowershop.model.UserInfo;

public interface UserInfoService {
    UserInfo findByUser (User user);
    void save(UserInfo userInfo);
}
