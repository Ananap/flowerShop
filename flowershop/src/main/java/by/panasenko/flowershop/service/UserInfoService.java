package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.UserInfo;

public interface UserInfoService {
    UserInfo findByUser (User user);
    void save(UserInfo userInfo);
}
