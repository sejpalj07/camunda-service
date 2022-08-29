package com.incedo.workflow.util;

import com.incedo.workflow.model.Order;
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
        Order order = (Order) execution.getVariable("order");
        String address = order.getAddress();
        String validationMessage = order.getValidationMessage();

        if (address == null || address.trim().isEmpty()) {
            execution.setVariable("validationError",true);
            validationMessage = validationMessage + "address is required for delivery order";
            order.setValidationMessage(validationMessage);
            execution.setVariable("order",order);
        }
    }

}
