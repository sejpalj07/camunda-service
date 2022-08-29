package com.incedo.workflow.util;

import com.incedo.workflow.model.Order;
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
        Order order = (Order) execution.getVariable("order");
        String pickupTime = order.getPickupTime();
        String validationMessage = order.getValidationMessage();

        if(pickupTime==null || pickupTime.trim().isEmpty() ){
            execution.setVariable("validationError",true);
            validationMessage = validationMessage + "pickup time is required for pickup order";
            order.setValidationMessage(validationMessage);
            execution.setVariable("order",order);
        }
    }

}
