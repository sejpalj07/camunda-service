package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDelegate implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		log.info("Test From Sub-Process.");
	}
}
