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
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

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
            boolean isValidDrinkOrder = Arrays.stream(FillDrinks.DrinksName.values())
                    .anyMatch((t) -> t.drink.equals(eachDrink));
            if(isValidDrinkOrder){
                ItemList.add(new Item(eachDrink, 0));
            }
        }
        execution.setVariable("ItemList", ItemList);
    }
}
