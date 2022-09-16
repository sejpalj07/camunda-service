package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("DeliverCompleted")
public class DeliverCompleted implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Entered DeliverCompleted");
	}
}