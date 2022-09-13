package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("FindTotalDelegate")
public class FindTotalDelegate implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Item> itemList = (List<Item>) execution.getVariable("itemList");
        int total = 0;
        for (Item item:itemList) {
            total = total + item.getPrice();
        }
        execution.setVariable("total", total);
        logger.info("Your total is : $" + total + ".00.");
    }
}
