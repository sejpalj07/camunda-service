package com.incedo.workflow.util;

import com.incedo.workflow.model.Side;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component("SideStatusDelegate")
public class SideStatusDelegate implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Side side = (Side) execution.getVariable("side");
        Map<String, Object> sideStatus = new HashMap<>();
//        side.getSideName()
        sideStatus.put("completedSide", side);
        execution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("SideStatusMessage")
                .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                .setVariables(sideStatus)
                .correlate();
        logger.info("Side Status Message sent");

    }
}

