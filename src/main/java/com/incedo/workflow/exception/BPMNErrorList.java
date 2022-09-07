package com.incedo.workflow.exception;

public enum BPMNErrorList {
    ERROR_INVALID_PIZZA_LIST("Error_P3001"),
    ERROR_INVALID_SIDE_LIST("Error_S3001"),
    ERROR_INVALID_DRINKS_LIST("Error_D3001"),

    ERROR_CORRELATION_PIZZA("Error_P3002"),
    ERROR_CORRELATION_SIDE("Error_S3002"),

    ERROR_PRICE_NOT_FOUND_PIZZA("Error_P3005"),
    ERROR_PRICE_NOT_FOUND_SIDE("Error_S3005"),
    ERROR_PRICE_NOT_FOUND_DRINKS("Error_D3005");
    private String errorCode;
    BPMNErrorList(String errorCode) {
        this.errorCode = errorCode;
    }
    @Override
    public String toString() {
        return errorCode;
    }
}
