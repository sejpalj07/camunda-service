package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("FindTotalDelegate")
public class FindTotalDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Item> itemList = (List<Item>) execution.getVariable("itemList");
        int total = 0;
        for (Item item : itemList) {
            total = total + item.getPrice();
        }
        execution.setVariable("total", total);
        log.info("Your total is : $" + total + ".00.");
    }
}
