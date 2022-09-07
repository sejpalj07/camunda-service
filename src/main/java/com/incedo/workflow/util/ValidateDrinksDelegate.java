package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.ListEmptyException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component("ValidateDrinksDelegate")
public class ValidateDrinksDelegate implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private enum DrinksName {
        SODA("Soda"),
        TEA("Iced Tea"),
        FANTA("Fanta"),
        WATER("Water");
        private final String drink;

        DrinksName(final String drink) {
            this.drink = drink;
        }

        @Override
        public String toString() {
            return drink;
        }
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<String> drinksList = (List<String>) execution.getVariable("drinksList");
        List<String> newDrinksList = new ArrayList<>();
        for (String eachDrink : drinksList) {
            boolean isValidDrinkOrder = Arrays.stream(ValidateDrinksDelegate.DrinksName.values())
                    .anyMatch((t) -> t.drink.equals(eachDrink));
            if (isValidDrinkOrder) {
                newDrinksList.add(eachDrink);
            }
        }
        if (newDrinksList.isEmpty()) {
            logger.error(BPMNErrorList.ERROR_INVALID_DRINKS_LIST + ": drinksList is Empty with Business key: " + execution.getProcessBusinessKey());
            throw new ListEmptyException(BPMNErrorList.ERROR_INVALID_DRINKS_LIST, "drinksList is Empty.");
        } else {
            execution.setVariable("drinksList", newDrinksList);
        }
    }
}
