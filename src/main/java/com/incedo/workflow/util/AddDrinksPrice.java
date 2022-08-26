package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AddDrinksPrice")
public class AddDrinksPrice implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String drink = (String) execution.getVariable("eachDrink");
        int drinkPrice = (int) execution.getVariable("drinkPrice");
        String id = execution.getProcessBusinessKey() + drink.replaceAll("\\s","");

        List<Item> ItemList = (ArrayList<Item>) execution.getVariable("ItemList");
        ItemList.add(new Item(id, drink, drinkPrice, null));
        execution.setVariable("ItemList", ItemList);

    }
}
