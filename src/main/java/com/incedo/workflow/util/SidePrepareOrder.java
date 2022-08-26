package com.incedo.workflow.util;

import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.Side;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("SidePrepareOrder")
public class SidePrepareOrder implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Order order = (Order) execution.getVariable("order");
        for (Side side : order.getSideList()) {
            Map<String, Object> sideName = new HashMap<>();
            sideName.put("side", side);
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("sideCreationMessage")
                    .setVariables(sideName)
                    .correlate();
        }

    }
}
