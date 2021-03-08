package com.flowershop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String address;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userInfo")
    private List<Payment> payments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public UserInfo createUserInfo(String firstName, String lastName, String address){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        return this;
    }
}
