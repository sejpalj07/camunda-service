package com.incedo.workflow.util;

import com.incedo.workflow.model.CustomerInfo;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("EnrichCustomer")
public class EnrichCustomer implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Entered EnrichCustomer");
        // api call will get informations from here
        CustomerInfo customerInfo  = (CustomerInfo) execution.getVariable("customerInfo");

        customerInfo.setName("person1");
        customerInfo.setAlternatePhoneNumber("9876543210");
        execution.setVariable("order",customerInfo);
    }

}