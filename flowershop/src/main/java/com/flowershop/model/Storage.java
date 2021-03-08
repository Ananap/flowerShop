package com.flowershop.model;

import com.flowershop.model.product.Flower;

import javax.persistence.*;

@Entity
@Table (name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int count;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flower_id")
    private Flower flower;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }
}
