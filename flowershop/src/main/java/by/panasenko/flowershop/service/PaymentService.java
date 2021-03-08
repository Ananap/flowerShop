package by.panasenko.flowershop.service;

import by.panasenko.flowershop.model.Payment;
import by.panasenko.flowershop.model.UserInfo;

public interface PaymentService {
    void addUserPayment(Payment payment, UserInfo userInfo);
    Payment findById(Integer id);
    void removeById(Integer id);
    void setDefaultPayment(Integer defaultPayment);
}
