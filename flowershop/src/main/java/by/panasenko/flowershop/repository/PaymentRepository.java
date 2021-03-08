package by.panasenko.flowershop.repository;

import by.panasenko.flowershop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
