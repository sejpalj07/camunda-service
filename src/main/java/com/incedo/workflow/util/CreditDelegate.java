package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("CreditDelegate")
public class CreditDelegate implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
             logger.info("Entered CreditDelegate");
        }

}
