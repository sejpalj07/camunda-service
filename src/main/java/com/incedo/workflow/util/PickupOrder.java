package com.incedo.workflow.util;

import java.util.Date;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("PickupOrder")
public class PickupOrder implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Entered PickupOrder");
		execution.setVariable("validationError", false);
		Date pickupTime = (Date) execution.getVariable("pickupTime");
		String validationMessage = (String) execution.getVariable("validationMessage");
		if (pickupTime == null) {
			execution.setVariable("validationError", true);
			validationMessage = validationMessage + "pickup time is required for pickup order";
			execution.setVariable("validationMessage", validationMessage);
			throw new BpmnError("1001");
		}
	}
}