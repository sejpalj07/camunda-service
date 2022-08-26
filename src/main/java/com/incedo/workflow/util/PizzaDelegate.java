package com.incedo.workflow.util;

import com.incedo.workflow.model.Pizza;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("PizzaDelegate")
public class PizzaDelegate implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Pizza pizza = (Pizza) execution.getVariable("pizza");
        logger.info("Pizza Name :" + pizza.getPizzaName());
        logger.info("Topping :" + pizza.getToppings());
    }
}
