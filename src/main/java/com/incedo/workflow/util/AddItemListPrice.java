package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("AddItemListPrice")
public class AddItemListPrice implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int itemPrice = (int) execution.getVariable("itemPrice");
        Item eachItem = (Item) execution.getVariable("eachItemOrder");
        List<Item> itemList = (ArrayList<Item>) execution.getVariable("itemList");
        for (Item item : itemList) {
            if (item.getItemId().equals(eachItem.getItemId())) {
                item.setPrice(itemPrice);
            }
        }
        log.info(String.valueOf(itemList));
        execution.setVariable("itemList", itemList);
    }
}
