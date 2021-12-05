package ru.fivt.dostavimvse.models;

/**
 * Created by akhtyamovpavel on 01.12.16.
 */
public enum OrderType {
    TIME("TIME"),
    PRICE("PRICE");


    private final String type;

    OrderType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
