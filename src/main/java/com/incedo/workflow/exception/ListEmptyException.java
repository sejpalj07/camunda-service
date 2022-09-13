package com.incedo.workflow.exception;

import org.camunda.bpm.engine.delegate.BpmnError;

public class ListEmptyException extends BpmnError {
    public ListEmptyException(BPMNErrorList ErrorList, String message) {
        super(ErrorList.toString(), message);
    }
}
