package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.PriceNotFound;
import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Pizza;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component("AddItemListPrice")
public class AddItemListPrice implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int itemPrice = (int) execution.getVariable("itemPrice");
        Item eachItem = (Item) execution.getVariable("eachItemOrder");
        List<Item> itemList = (ArrayList<Item>) execution.getVariable("itemList");
        for (Item item:itemList) {
            if(item.getItemId().equals(eachItem.getItemId())){
                item.setPrice(itemPrice);
            }
        }
        logger.info(String.valueOf(itemList));
        execution.setVariable("itemList", itemList);
    }
}
