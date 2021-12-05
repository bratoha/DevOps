package ru.fivt.dostavimvse.models;

/**
 * Created by akhtyamovpavel on 01.12.16.
 */
public enum LegType {
    AVIA("avia"),
    TRAIN("train");


    private final String type;

    LegType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return type;   
    }
}
