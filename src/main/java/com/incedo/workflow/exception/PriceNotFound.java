package com.incedo.workflow.exception;

import org.camunda.bpm.engine.delegate.BpmnError;

public class PriceNotFound extends BpmnError {
    public PriceNotFound(BPMNErrorList errorCode, String message) {
        super(String.valueOf(errorCode), message);
    }
}
