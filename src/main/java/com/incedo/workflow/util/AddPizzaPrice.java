package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Pizza;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component("AddPizzaPrice")
public class AddPizzaPrice implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Pizza pizza = (Pizza) execution.getVariable("completedPizza");
        int pizzaPrice = (int) execution.getVariable("pizzaPrice");
//        execution.removeVariable("completedPizza");
        List<Item> ItemList = (ArrayList<Item>) execution.getVariable("ItemList");
        ItemList.add(new Item(pizza.getPizzaId(), pizza.getPizzaName(), pizzaPrice, pizza.getToppings()));
        execution.setVariable("ItemList", ItemList);
    }
}
