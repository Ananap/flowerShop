package com.flowershop.model;

import com.flowershop.model.product.Flower;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "flower_type")
public class FlowerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(value = EnumType.STRING)
    private FlowerCategory category;

    @OneToMany(mappedBy = "flowerType")
    private List<Flower> flower;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FlowerCategory getCategory() {
        return category;
    }

    public void setCategory(FlowerCategory category) {
        this.category = category;
    }

    public List<Flower> getFlower() {
        return flower;
    }

    public void setFlower(List<Flower> flower) {
        this.flower = flower;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
