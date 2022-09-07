package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("PickupOrder")
public class PickupOrder implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Entered PickupOrder");
        execution.setVariable("validationError",false);
        String pickupTime = (String) execution.getVariable("pickupTime");
        String validationMessage = (String) execution.getVariable("validationMessage");

        if(pickupTime==null || pickupTime.trim().isEmpty() ){
            execution.setVariable("validationError",true);
            validationMessage = validationMessage + "pickup time is required for pickup order";
            execution.setVariable("validationMessage",validationMessage);
            throw new BpmnError("1001"); // ValidationError
        }
    }

}
