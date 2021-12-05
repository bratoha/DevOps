package ru.fivt.dostavimvse.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;

/**
 * Created by akhtyamovpavel on 30.11.16.
 */
@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DecimalMin("0.0")
    @Column(name = "PRICE")
    private Double price;

    @DecimalMin("0.0")
    @Column(name = "WEIGHT")
    private Double weight;

    @ManyToOne()
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    public Integer getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
