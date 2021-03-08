package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.UserInfo;
import by.panasenko.flowershop.repository.UserInfoRepository;
import by.panasenko.flowershop.service.UserInfoService;
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
