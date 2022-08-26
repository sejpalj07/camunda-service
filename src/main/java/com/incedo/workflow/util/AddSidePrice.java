package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Side;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("AddSidePrice")
public class AddSidePrice implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Side side = (Side) execution.getVariable("completedSide");
        int sidePrice = (int) execution.getVariable("sidePrice");
        List<Item> ItemList = (ArrayList<Item>) execution.getVariable("ItemList");
        ItemList.add(new Item(side.getSideId(), side.getSideName(), sidePrice, null));
        execution.setVariable("ItemList", ItemList);
    }
}
