package ru.fivt.dostavimvse.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by akhtyamovpavel on 30.11.16.
 */
@Entity
@Table(name = "ROUTE")
public class Route {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Order getOrder() {
        return order;
    }

    @OneToOne()
    @JoinColumn(name = "ORDER_ID", nullable = false, referencedColumnName = "ID")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "CURRENT_ROUTE_LEG_ID", nullable = true, referencedColumnName = "ID")
    private RouteLeg currentLeg;



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "route", cascade = CascadeType.ALL)
    private Set<RouteLeg> routeLegs = new HashSet<>();

    public Set<RouteLeg> getRouteLegs() {
        return routeLegs;
    }

    public void setRouteLegs(Set<RouteLeg> legSet) {
        this.routeLegs = legSet;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public RouteLeg getCurrentLeg() {
        return currentLeg;
    }

    public void setCurrentLeg(RouteLeg currentLeg) {
        this.currentLeg = currentLeg;
    }
}
