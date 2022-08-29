package com.incedo.workflow.util;

import com.incedo.workflow.model.Pizza;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("ValidatePizzaDelegate")
public class ValidatePizzaDelegate implements JavaDelegate {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private enum PizzaName {
        veggie("Veggie Pizza"),
        cheese("Cheese Pizza"),
        meat("Meat Pizza");
        private final String pizza;
        PizzaName(final String pizza) {
            this.pizza = pizza;
        }
        @Override
        public String toString() {
            return pizza;
        }
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Pizza> pizzaList = (List<Pizza>) execution.getVariable("pizzaList");
        List<Pizza> newPizzaList = new ArrayList<>();
        logger.info("start validate pizza");
//        String bKey = (String) execution.getProcessBusinessKey();
//        for (Pizza pizza : order.getPizzaList()) {
        for (Pizza pizza : pizzaList) {
            String name = pizza.getPizzaName();
            boolean isValidPizzaOrder = Arrays.stream(PizzaName.values())
                    .anyMatch((t) -> t.pizza.equals(name));
            if (isValidPizzaOrder) {
                newPizzaList.add(pizza);
            }
        }
        execution.setVariable("pizzaList", newPizzaList);
        logger.info("end validate pizza");
    }
}
