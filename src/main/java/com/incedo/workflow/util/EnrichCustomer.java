package com.incedo.workflow.util;

import com.incedo.workflow.model.CustomerInfo;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("EnrichCustomer")
public class EnrichCustomer implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        CustomerInfo customerInfo = (CustomerInfo) execution.getVariable("customerInfo");
        log.info("From Enrich Customer: " + customerInfo);
//        execution.setVariable("order", customerInfo);
    }
}