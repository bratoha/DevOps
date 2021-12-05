package ru.fivt.dostavimvse.models;

/**
 * Created by akhtyamovpavel on 01.12.16.
 */
public enum OrderStatus {
    WAIT_CREATE("WAIT_CREATE"),
    WAIT_CHANGE("WAIT_CHANGE"),
    MOVING("MOVING"),
    READY("READY"),
    COMPLETED("COMPLETED");


    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
