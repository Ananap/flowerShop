package by.panasenko.flowershop.service.impl;

import by.panasenko.flowershop.model.Payment;
import by.panasenko.flowershop.model.UserInfo;
import by.panasenko.flowershop.repository.PaymentRepository;
import by.panasenko.flowershop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public void addUserPayment(Payment payment, UserInfo userInfo) {
        payment.setUserInfo(userInfo);
        payment.setDefaultPayment(true);
        userInfo.getPayments().add(payment);
        paymentRepository.save(payment);
    }

    public Payment findById(Integer id) {
        return paymentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void removeById(Integer id) {
        paymentRepository.deleteById(id);
    }

    public void setDefaultPayment(Integer defaultPayment) {
        List<Payment> userPaymentList = paymentRepository.findAll();
        for(Payment userPayment: userPaymentList) {
            if(userPayment.getId() == defaultPayment) {
                userPayment.setDefaultPayment(true);
            } else {
                userPayment.setDefaultPayment(false);
            }
            paymentRepository.save(userPayment);
        }
    }
}
