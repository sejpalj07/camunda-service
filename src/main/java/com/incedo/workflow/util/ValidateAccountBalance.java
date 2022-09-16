package com.incedo.workflow.util;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ValidateAccountBalance")
public class ValidateAccountBalance implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Entered ValidateAccountBalance");
        execution.setVariable("sufficientBalance", true);
    }
}