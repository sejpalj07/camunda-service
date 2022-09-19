package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.InvalidItemException;
import com.incedo.workflow.exception.ListEmptyException;
import com.incedo.workflow.model.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component("ValidatePizzaDelegate")
public class ValidatePizzaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>" + execution.getProcessInstance().getProcessBusinessKey());
        List<Pizza> pizzaList = (List<Pizza>) execution.getVariable("pizzaList");
        List<Pizza> newPizzaList = new ArrayList<>();
        for (Pizza pizza : pizzaList) {
            String name = pizza.getPizzaName();
            boolean isValidPizzaOrder = Arrays.stream(PizzaName.values())
                    .anyMatch(t -> t.pizza.equals(name));
            if (isValidPizzaOrder) {
                newPizzaList.add(pizza);
            } else {
                log.error(BPMNErrorList.ERROR_ITEM_INVALID + ": InValid Pizza Item: " + pizza + "\n with Business Key: " + execution.getProcessBusinessKey());
                throw new InvalidItemException(BPMNErrorList.ERROR_ITEM_INVALID, "InValid Pizza Item" + pizza + " with Business Key: " + execution.getProcessBusinessKey());
            }
        }
        if (newPizzaList.isEmpty()) {
            log.error(BPMNErrorList.ERROR_EMPTY_LIST + ": PizzaList is Empty with Business key: " + execution.getProcessBusinessKey());
            throw new ListEmptyException(BPMNErrorList.ERROR_EMPTY_LIST, "PizzaList is Empty, with Business Key" + execution.getProcessBusinessKey());
        } else {
            execution.setVariable("pizzaList", newPizzaList);
        }
        log.info("Pizza Validation done.");
    }

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
}
