package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.User;
import by.panasenko.flowershop.model.security.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Integer> {
    PasswordToken findByToken(String token);

    PasswordToken findByUser(User user);

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query("delete from PasswordToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
