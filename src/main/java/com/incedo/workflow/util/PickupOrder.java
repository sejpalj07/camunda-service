package com.incedo.workflow.util;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("PickupOrder")
public class PickupOrder implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Entered PickupOrder");
        execution.setVariable("validationError", false);
        String pickupTime = (String) execution.getVariable("pickupTime");
        String validationMessage = (String) execution.getVariable("validationMessage");

        if (pickupTime == null || pickupTime.trim().isEmpty()) {
            execution.setVariable("validationError", true);
            validationMessage = validationMessage + "pickup time is required for pickup order";
            execution.setVariable("validationMessage", validationMessage);
            throw new BpmnError("1001"); // ValidationError
        }
    }

}
