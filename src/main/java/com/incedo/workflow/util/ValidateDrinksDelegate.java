<<<<<<< HEAD:src/main/java/com/incedo/workflow/util/FillDrinks.java
package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.Pizza;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component("FillDrinks")
public class FillDrinks implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private enum DrinksName {
        soda("Soda"),
        tea("Iced Tea"),
        water("water");
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
        Order order = (Order) execution.getVariable("order");
        List<Item> ItemList = (ArrayList<Item>) execution.getVariable("ItemList");
        for (String eachDrink : order.getDrinksList()) {
            boolean isValidDrinkOrder = Arrays.stream(DrinksName.values())
                    .anyMatch((t) -> t.drink.equals(eachDrink));
            if(isValidDrinkOrder){
                ItemList.add(new Item(eachDrink, 0));
            }
        }
        execution.setVariable("ItemList", ItemList);
    }
}
=======
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
        water("water");
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
        execution.setVariable("drinksList", newDrinksList);
    }
}
>>>>>>> f13bbabc373b7ae220dcb6ef85e16acf21ad8740:src/main/java/com/incedo/workflow/util/ValidateDrinksDelegate.java
