package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.ListEmptyException;
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
        BREAD("Garlic Bread"),
        FRIES("Fries"),
        ABC("abc"),
        WINGS("Wings");
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
            }
        }
        if (newSideList.isEmpty()) {
            logger.error(BPMNErrorList.ERROR_INVALID_SIDE_LIST + ": sideList is Empty with Business key: " + execution.getProcessBusinessKey());
            throw new ListEmptyException(BPMNErrorList.ERROR_INVALID_SIDE_LIST, "sideList is Empty.");
        } else {
            execution.setVariable("sideList", newSideList);
        }
    }
}
