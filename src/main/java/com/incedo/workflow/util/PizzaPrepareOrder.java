package com.incedo.workflow.util;

import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.Pizza;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("PizzaPrepareOrder")
public class PizzaPrepareOrder implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("PizzaPrepareOrder start");
//        for (Pizza pizza : order.getPizzaList()) {
        Pizza pizza = (Pizza) execution.getVariable("eachPizza");
        Map<String, Object> pizzaName = new HashMap<>();
        pizzaName.put("pizza", pizza);

        execution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("PizzaCreationMessage")
                .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                .setVariables(pizzaName)
                .correlate();
        logger.info("pizza: " + pizza);

    }
}
