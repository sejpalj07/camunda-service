package com.incedo.workflow.exception;

import org.camunda.bpm.engine.delegate.BpmnError;

public class MessageCorrelationException extends BpmnError {
    public MessageCorrelationException(BPMNErrorList errorCode, String message) {
        super(String.valueOf(errorCode), message);
    }
}
