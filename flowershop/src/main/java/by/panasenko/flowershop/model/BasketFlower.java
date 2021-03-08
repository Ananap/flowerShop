package by.panasenko.flowershop.model;

import by.panasenko.flowershop.model.product.Flower;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "basket_flower")
public class BasketFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int count;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "flower_id")
    private Flower flower;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

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

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public void setSubTotalCount(double price, int count) {
        this.subTotal = new BigDecimal(price).multiply(new BigDecimal(count));
    }
}
