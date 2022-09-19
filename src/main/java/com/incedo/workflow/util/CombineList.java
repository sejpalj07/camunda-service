package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Pizza;
import com.incedo.workflow.model.Side;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component("CombineList")
public class CombineList implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Item> itemList = new ArrayList<>();
        List<Pizza> pizzaList = (List<Pizza>) execution.getVariable("pizzaList");
        List<Side> sideList = (List<Side>) execution.getVariable("sideList");
        List<String> drinksList = (List<String>) execution.getVariable("drinksList");
        for (Pizza pizza : pizzaList) {
            itemList.add(new Item(pizza.getPizzaId(), pizza.getPizzaName(), 0, pizza.getToppings()));
        }
        for (Side side : sideList) {
            itemList.add(new Item(side.getSideId(), side.getSideName(), 0, null));
        }
        for (String drinks : drinksList) {
            String id = execution.getProcessBusinessKey() + drinks.replaceAll("\\s", "");
            itemList.add(new Item(id, drinks, 0, null));
        }
        execution.setVariable("itemList", itemList);
    }
}
