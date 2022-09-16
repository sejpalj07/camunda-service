package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.incedo.workflow.model.CustomerInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("EnrichCustomer")
public class EnrichCustomer implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Entered EnrichCustomer");
		CustomerInfo customerInfo = (CustomerInfo) execution.getVariable("customerInfo");
		customerInfo.setName("person1");
		customerInfo.setAlternatePhoneNumber("9876543210");
		execution.setVariable("order", customerInfo);
	}
}