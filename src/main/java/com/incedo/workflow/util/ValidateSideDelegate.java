package com.incedo.workflow.util;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.Side;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("ValidateSideDelegate")
public class ValidateSideDelegate implements JavaDelegate {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private enum SideName {
        bread("Garlic Bread"),
        fries("Fries"),
        wings("Wings");
        private final String side;
        SideName(final String side) {
            this.side = side;
        }
        @Override
        public String toString() {
            return side;
        }
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Order order = (Order) execution.getVariable("order");
        List<Item> ItemList = (ArrayList<Item>) execution.getVariable("ItemList");
        for (Side side : order.getSideList()) {
            String name = side.getSideName();
            boolean isValidSideOrder = Arrays.stream(SideName.values())
                    .anyMatch((t) -> t.side.equals(name));
            if(isValidSideOrder){
                ItemList.add(new Item(name, 0));
            }
            execution.setVariable("isValidSideOrder", isValidSideOrder);
        }
        execution.setVariable("ItemList", ItemList);
    }
}
