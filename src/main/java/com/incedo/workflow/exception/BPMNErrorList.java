package com.incedo.workflow.exception;

public enum BPMNErrorList {
    ERROR_ITEM_INVALID("Error_6000"),
    ERROR_EMPTY_LIST("Error_6001"),
    ERROR_MESSAGE_NOT_CORRELATE("Error_6002"),
    ERROR_PRICE_NOT_FOUND("Error_6005");
    private String errorCode;

    BPMNErrorList(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return errorCode;
    }
}
