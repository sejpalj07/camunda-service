package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Order;
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
        soda("Soda"),
        tea("Iced Tea"),
        water("Water");
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
        //execution.getProcessEngine().getRuntimeService()
        execution.setVariable("drinksList", newDrinksList);
    }
}
