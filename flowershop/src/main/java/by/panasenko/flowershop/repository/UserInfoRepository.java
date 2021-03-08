package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUser(User user);
}
