package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.InvalidItemException;
import com.incedo.workflow.exception.ListEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component("ValidateDrinksDelegate")
public class ValidateDrinksDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<String> drinksList = (List<String>) execution.getVariable("drinksList");
        List<String> newDrinksList = new ArrayList<>();
        for (String eachDrink : drinksList) {
            boolean isValidDrinkOrder = Arrays.stream(ValidateDrinksDelegate.DrinksName.values())
                    .anyMatch(t -> t.drink.equals(eachDrink));
            if (isValidDrinkOrder) {
                newDrinksList.add(eachDrink);
            } else {
                log.error(BPMNErrorList.ERROR_ITEM_INVALID + ": InValid Drinks Item: " + eachDrink + "\n with Business Key: " + execution.getProcessBusinessKey());
                throw new InvalidItemException(BPMNErrorList.ERROR_ITEM_INVALID, "InValid Drinks Item" + eachDrink + " with Business Key: " + execution.getProcessBusinessKey());
            }
        }
        if (newDrinksList.isEmpty()) {
            log.error(BPMNErrorList.ERROR_EMPTY_LIST + ": drinksList is Empty, with Business key: " + execution.getProcessBusinessKey());
            throw new ListEmptyException(BPMNErrorList.ERROR_EMPTY_LIST, "drinksList is Empty, with Business key: " + execution.getProcessBusinessKey());
        } else {
            execution.setVariable("drinksList", newDrinksList);
        }
    }

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
}
