package com.incedo.workflow.exception;

import org.camunda.bpm.engine.delegate.BpmnError;

public class EquipmentFailure extends BpmnError {
    public EquipmentFailure(BPMNErrorList errorCode, String message) {
        super(String.valueOf(errorCode), message);
    }
}
