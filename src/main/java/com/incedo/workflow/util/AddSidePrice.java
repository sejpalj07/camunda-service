package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.PriceNotFound;
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
        List<Item> ItemList = (ArrayList<Item>) execution.getVariable("ItemList");
        Side side = null;
        try{
            side = (Side) execution.getVariable("completedSide");
            int sidePrice = (int) execution.getVariable("sidePrice");
            ItemList.add(new Item(side.getSideId(), side.getSideName(), sidePrice, null));
            execution.setVariable("ItemList", ItemList);
        } catch (NullPointerException ex){
            logger.error(BPMNErrorList.ERROR_PRICE_NOT_FOUND_SIDE + ": "+ side + " can't added to the list. Price Of the Item not Found.");
            throw new PriceNotFound(BPMNErrorList.ERROR_PRICE_NOT_FOUND_SIDE, side + " can't added to the list. Price Of the Item not Found.");
        }
    }
}
