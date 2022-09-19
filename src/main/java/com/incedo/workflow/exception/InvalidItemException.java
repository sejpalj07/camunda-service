package com.incedo.workflow.exception;

import org.camunda.bpm.engine.delegate.BpmnError;

public class InvalidItemException extends BpmnError {
    public InvalidItemException(BPMNErrorList ErrorList, String message) {
        super(ErrorList.toString(), message);
    }
}
