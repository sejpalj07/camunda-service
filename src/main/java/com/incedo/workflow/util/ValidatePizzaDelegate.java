package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.ListEmptyException;
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
        VEGGIE("Veggie Pizza"),
        CHEESE("Cheese Pizza"),
        MEAT("Meat Pizza"),
        CHICKEN("Chicken Pizza");
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
        for (Pizza pizza : pizzaList) {
            String name = pizza.getPizzaName();
            boolean isValidPizzaOrder = Arrays.stream(PizzaName.values())
                    .anyMatch((t) -> t.pizza.equals(name));
            if (isValidPizzaOrder) {
                newPizzaList.add(pizza);
            }
        }
        if (newPizzaList.isEmpty()) {
            logger.error(BPMNErrorList.ERROR_INVALID_PIZZA_LIST + ": PizzaList is Empty with Business key: " + execution.getProcessBusinessKey());
            throw new ListEmptyException(BPMNErrorList.ERROR_INVALID_PIZZA_LIST, "PizzaList is Empty.");
        } else {
            execution.setVariable("pizzaList", newPizzaList);
        }

        logger.info("end validate pizza");
    }
}
