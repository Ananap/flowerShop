package com.flowershop.repository;

import com.flowershop.model.User;
import com.flowershop.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUser(User user);
}
