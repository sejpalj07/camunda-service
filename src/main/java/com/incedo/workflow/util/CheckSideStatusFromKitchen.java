package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("CheckSideStatusFromKitchen")
public class CheckSideStatusFromKitchen implements JavaDelegate {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(DelegateExecution execution) throws Exception {
//        List<Item> itemList = (ArrayList<Item>) execution.getVariable("itemList");
//        String drink = null;
//        try{
//            drink = (String) execution.getVariable("eachDrink");
//            int drinkPrice = (int) execution.getVariable("drinkPrice");
//            String id = execution.getProcessBusinessKey() + drink.replaceAll("\\s","");
//            itemList.add(new Item(id, drink, drinkPrice, null));
//            execution.setVariable("itemList", itemList);
//        } catch (NullPointerException ex){
//            logger.error(BPMNErrorList.ERROR_PRICE_NOT_FOUND + ": "+ drink + " can't added to the list. Price Of the Item not Found.");
//            throw new PriceNotFound(BPMNErrorList.ERROR_PRICE_NOT_FOUND, drink + " can't added to the list. Price Of the Item not Found.");
//        }
        logger.info(">>CheckSideStatusFromKitchen");

    }
}
