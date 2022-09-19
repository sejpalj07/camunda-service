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
@Component("SideStatusDelegate")
public class SideStatusDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Side side = (Side) execution.getVariable("side");
        String bKey = execution.getProcessInstance().getProcessBusinessKey();
        Map<String, Object> sideStatus = new HashMap<>();
        sideStatus.put("completedSide", side);
        Map<String, Object> correlationKeys = new HashMap<>();
        correlationKeys.put(bKey, bKey);

        List<EventSubscription> eventSubscriptions = execution.getProcessEngineServices()
                .getRuntimeService()
                .createEventSubscriptionQuery()
                .eventName("SideStatusMessage")
                .eventType("message").list();

        if (eventSubscriptions.isEmpty()) {
            log.error("Message correlation failed from back house. No Process is ready to receive the message. ");
            throw new MessageCorrelationException(BPMNErrorList.ERROR_MESSAGE_NOT_CORRELATE_FROM_BACK_HOUSE, "Message correlation failed from back house. No Process is ready to receive the message.");
        } else {
            log.info("event id : " + eventSubscriptions.get(0));
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("SideStatusMessage")
                    .processInstanceBusinessKey(bKey)
                    .setVariables(sideStatus)
                    .correlate();
            log.info("Side Status Message sent");
        }
    }
}
