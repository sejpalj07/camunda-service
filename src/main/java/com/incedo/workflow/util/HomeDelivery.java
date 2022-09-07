package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("HomeDelivery")
public class HomeDelivery implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Entered HomeDelivery");
        execution.setVariable("validationError",false);
        String address = (String) execution.getVariable("address");
        String validationMessage = (String) execution.getVariable("validationMessage");

        if (address == null || address.trim().isEmpty()) {
            execution.setVariable("validationError",true);
            validationMessage = validationMessage + "address is required for delivery order";
            execution.setVariable("validationMessage",validationMessage);
            throw new BpmnError("1001"); // ValidationError
        }
    }

}
