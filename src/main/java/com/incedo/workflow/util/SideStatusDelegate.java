package com.incedo.workflow.util;

import com.incedo.workflow.model.Side;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
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
        Map<String, Object> sideStatus = new HashMap<>();
        sideStatus.put("completedSide", side);

//        SubscriptionQuery subscriptionQuery = runtimeService.createEventSubscriptionQuery()
//                .eventName("my_message")
//                .eventType("message");
//        List<EventSubscription> eventSubscriptions = subscriptionQuery.list();

        try{
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("SideStatusMessage")
                    .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                    .setVariables(sideStatus)
                    .correlate();
            log.info("Side Status Message sent");
        } catch (Exception ex){
            log.error("MismatchingMessageCorrelationException:  Cannot correlate message 'SideStatusMessage': No process definition or execution matches the parameters");
            throw new BpmnError("Error_B000", "failed.");
        }


    }
}
