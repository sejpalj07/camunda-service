package com.incedo.workflow.util;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("HomeDelivery")
public class HomeDelivery implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Entered HomeDelivery");
        execution.setVariable("validationError", false);
        String address = (String) execution.getVariable("address");
        String validationMessage = (String) execution.getVariable("validationMessage");
        if (address == null || address.trim().isEmpty()) {
            execution.setVariable("validationError", true);
            validationMessage = validationMessage + "address is required for delivery order";
            execution.setVariable("validationMessage", validationMessage);
            throw new BpmnError("1001");
        }
    }
}
