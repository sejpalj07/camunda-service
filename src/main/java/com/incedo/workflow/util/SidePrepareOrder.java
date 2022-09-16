package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.MessageCorrelationException;
import com.incedo.workflow.model.Side;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("SidePrepareOrder")
public class SidePrepareOrder implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Side side = (Side) execution.getVariable("eachSide");
        Map<String, Object> sideName = new HashMap<>();
        sideName.put("side", side);

        List<EventSubscription> eventSubscriptions = execution.getProcessEngineServices()
                .getRuntimeService()
                .createEventSubscriptionQuery()
                .eventName("sideCreationMessage")
                .eventType("message").list();

        if (eventSubscriptions.isEmpty()) {
            log.error("No Process is ready to receive the message. ");
            throw new MessageCorrelationException(BPMNErrorList.ERROR_MESSAGE_NOT_CORRELATE, "No Process is ready to receive the message. ");
        } else {
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("sideCreationMessage")
                    .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                    .setVariables(sideName)
                    .correlate();
        }

    }
}
