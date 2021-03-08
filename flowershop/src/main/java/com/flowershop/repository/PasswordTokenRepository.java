package com.flowershop.repository;

import com.flowershop.model.User;
import com.flowershop.model.security.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Stream;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Integer> {
    PasswordToken findByToken(String token);

    PasswordToken findByUser(User user);

    Stream<PasswordToken> findAllByExpiryDateLessThan(LocalDateTime expiryDate);

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query("delete from PasswordToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
