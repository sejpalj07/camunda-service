package com.incedo.workflow.util;

import com.incedo.workflow.model.Side;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("ValidateSideDelegate")
public class ValidateSideDelegate implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        List<Side> sideList = (List<Side>) execution.getVariable("sideList");
        List<Side> newSideList = new ArrayList<>();
        logger.info("Before removal:" + sideList);
        for (Side side : sideList) {
            String name = side.getSideName();
            boolean isValidSideOrder = Arrays.stream(ValidateSideDelegate.SideName.values())
                    .anyMatch((t) -> t.side.equals(name));
            if(isValidSideOrder){
                newSideList.add(side);
               //delete side
               // sideList.remove(side);
//                logger.info("After removal:" + sideList);
            }
        }
        execution.setVariable("sideList", newSideList);
    }
}
