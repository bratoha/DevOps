package ru.fivt.dostavimvse.models;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by akhtyamovpavel on 30.11.16.
 */

@Entity
@Table(name = "ROUTE_LEG")
public class RouteLeg {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;


    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    public Leg getLeg() {
        return leg;
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    @ManyToOne()
    @JoinColumn(name = "LEG_ID", nullable = false)
    private Leg leg;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROUTE_ID", nullable = false)
    private Route route;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
