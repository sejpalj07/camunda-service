package com.incedo.workflow.util;

import com.incedo.workflow.model.Side;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component("SideStatusDelegate")
public class SideStatusDelegate implements JavaDelegate {

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
        log.info("Side Status Message sent");

    }
}
