package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component("PizzaStatusDelegate")
public class PizzaStatusDelegate implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Map<String, Object> pizzaStatus = new HashMap<>();
        pizzaStatus.put("pizzaStatus", "Order Completed");
        execution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("PizzaStatusMessage")
                .setVariables(pizzaStatus)
                .correlate();
        logger.info("PizzaStatusMessage sent");

    }
}
