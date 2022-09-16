package com.incedo.workflow.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("UpdatePaymentStatus")
public class UpdatePaymentStatus implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Entered UpdatePaymentStatus");
		Boolean sufficientBalance = (Boolean) execution.getVariable("sufficientBalance");
		log.info("sufficientBalance " + sufficientBalance + " payment successful");
		log.info(">>>>>>>>>>>>>>>>>>" + execution.getProcessInstance().getProcessBusinessKey());
		Map<String, Object> variables = new HashMap<>();
		variables.put("sufficientBalance", sufficientBalance);
		List<EventSubscription> eventSubscriptions = execution.getProcessEngineServices().getRuntimeService()
				.createEventSubscriptionQuery().eventName("paymentcompletionmessage").eventType("message").list();
		if (eventSubscriptions.isEmpty()) {
			log.error("Payment System couldn't correlate the message.");
		} else {
			execution.getProcessEngineServices().getRuntimeService()
					.createMessageCorrelation("paymentcompletionmessage")
					.processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
					.setVariables(variables).correlate();
			log.info("payment completion Message sent");
		}

	}
}
