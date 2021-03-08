package com.flowershop.service;

import com.flowershop.model.Payment;
import com.flowershop.model.UserInfo;

public interface PaymentService {
    void addUserPayment(Payment payment, UserInfo userInfo);
    Payment findById(Integer id);
    void removeById(Integer id);
    void setDefaultPayment(Integer defaultPayment);
}
