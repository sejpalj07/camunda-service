package com.incedo.workflow.util;

import com.incedo.workflow.model.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("PizzaDelegate")
public class PizzaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Pizza pizza = (Pizza) execution.getVariable("pizza");
        log.info("Pizza Name :" + pizza.getPizzaName());
        log.info("Topping :" + pizza.getToppings());
    }
}
