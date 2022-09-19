package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.MessageCorrelationException;
import com.incedo.workflow.model.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("PizzaPrepareOrder")
public class PizzaPrepareOrder implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("PizzaPrepareOrder start");
        Pizza pizza = (Pizza) execution.getVariable("eachPizza");
        Map<String, Object> pizzaName = new HashMap<>();
        pizzaName.put("pizza", pizza);

        List<EventSubscription> eventSubscriptions = execution.getProcessEngineServices()
                .getRuntimeService()
                .createEventSubscriptionQuery()
                .eventName("PizzaCreationMessage")
                .eventType("message").list();

        if (eventSubscriptions.isEmpty()) {
            log.error("Back house Process isn't ready to receive the message. ");
            throw new MessageCorrelationException(BPMNErrorList.ERROR_MESSAGE_NOT_CORRELATE, "Back house process isn't ready to receive the message. ");
        } else {
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("PizzaCreationMessage")
                    .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                    .setVariables(pizzaName)
                    .correlate();
        }
    }
}
