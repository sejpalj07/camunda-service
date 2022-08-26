package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Order;
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
        Order order = (Order) execution.getVariable("order");
        List<Item> ItemList = (ArrayList<Item>) execution.getVariable("ItemList");
        for (Pizza pizza : order.getPizzaList()) {
            String name = pizza.getPizzaName();
            boolean isValidPizzaOrder = Arrays.stream(PizzaName.values())
                    .anyMatch((t) -> t.pizza.equals(name));
            if(isValidPizzaOrder){
                ItemList.add( new Item(name, 0));
            }
            execution.setVariable("isValidPizzaOrder", isValidPizzaOrder);
        }
        execution.setVariable("ItemList", ItemList);
    }
}
