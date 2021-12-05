package ru.fivt.dostavimvse.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by akhtyamovpavel on 29.11.16.
 */
@Entity
@Table(name = "LEG")
public class Leg implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(0)
    @Column(name = "START_VERTEX")
    private Integer startVertex;

    @Min(0)
    @Column(name = "END_VERTEX")
    private Integer endVertex;

    @DecimalMin("0.0")
    @Column(name = "MAX_WEIGHT")
    private Double maxWeight;

    @DecimalMin("0.0")
    @Column(name = "BASE_PRICE")
    private Double basePrice;

    @Column(name="LEG_TYPE", columnDefinition = "enum('AVIA';'TRAIN')")
    @Enumerated(EnumType.STRING)
    private LegType legType;

    @Min(1)
    @Column(name = "SEND_TIME")
    private Integer sendTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }


    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public LegType getLegType() {
        return legType;
    }

    public void setLegType(LegType legType) {
        this.legType = legType;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSendTime() {
        return sendTime;
    }

}
