package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.model.Side;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("SidePrepareOrder")
public class SidePrepareOrder implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Side side = (Side) execution.getVariable("eachSide");
        Map<String, Object> sideName = new HashMap<>();
        sideName.put("side", side);
        try {
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("sideCreationMessage")
                    .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                    .setVariables(sideName)
                    .correlate();
        } catch (Exception ex) {
            String msg = "Can't Correlate " + side + "because of " + ex.getMessage();
            log.error(BPMNErrorList.ERROR_MESSAGE_NOT_CORRELATE + msg + "with Business key: " + execution.getProcessBusinessKey());
//            throw new ListEmptyException(BPMNErrorList.ERROR_MESSAGE_NOT_CORRELATE, msg);
        }


    }
}
