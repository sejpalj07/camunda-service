package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.ListEmptyException;
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
        Side side = (Side) execution.getVariable("eachSide");
        Map<String, Object> sideName = new HashMap<>();
        sideName.put("side", side);
        try{
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("sideCreationMessage")
                    .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                    .setVariables(sideName)
                    .correlate();
        } catch (Exception ex) {
            String msg = "Can't Correlate " + side + "because of " + ex.getMessage();
            logger.error(BPMNErrorList.ERROR_CORRELATION_SIDE + msg + "with Business key: " + execution.getProcessBusinessKey());
            throw new ListEmptyException(BPMNErrorList.ERROR_CORRELATION_SIDE, msg);
        }


    }
}
