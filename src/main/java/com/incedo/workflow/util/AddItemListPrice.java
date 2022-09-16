package com.incedo.workflow.util;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.PriceNotFound;
import com.incedo.workflow.model.Item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("AddItemListPrice")
public class AddItemListPrice implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		try {
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
		} catch (Exception ex) {
			log.error("Price not Found.");
			throw new PriceNotFound(BPMNErrorList.ERROR_PRICE_NOT_FOUND, "Price not found in dmn table.");
		}
	}
}