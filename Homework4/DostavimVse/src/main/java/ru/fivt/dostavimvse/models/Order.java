package ru.fivt.dostavimvse.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by akhtyamovpavel on 30.11.16.
 */

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();


    @Column(name = "START_DATE")
    private LocalDateTime startDate;


    @Column(name = "ORDER_TYPE", columnDefinition = "enum('TIME';'PRICE')")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "ORDER_STATUS", columnDefinition = "enum('WAIT_CREATE';'WAIT_CHANGE';'MOVING';'READY';'COMPLETED')")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Min(0)
    @Column(name = "START_VERTEX", nullable = false)
    private Integer startVertex;

    @Min(0)
    @Column(name = "END_VERTEX", nullable = false)
    private Integer endVertex;

    @ManyToOne()
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    public Client getReceiver() {
        return receiver;
    }

    public void setReceiver(Client receiver) {
        this.receiver = receiver;
    }

    @ManyToOne()
    @JoinColumn(name = "RECEIVER_ID", nullable = false)
    private Client receiver;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private Route route;


    public Integer getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Integer startVertex) {
        this.startVertex = startVertex;
    }

    public Integer getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(Integer endVertex) {
        this.endVertex = endVertex;
    }





    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Client getClient() {
        return client;
    }

    public Integer getId() {
        return id;
    }


    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getOverallPrice() {
        double sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    public double getOverallWeight() {
        double sum = 0;
        for (Product product : products) {
            sum += product.getWeight();
        }
        return sum;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
