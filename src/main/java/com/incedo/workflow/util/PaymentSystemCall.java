package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("PaymentSystemCall")
public class PaymentSystemCall implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Entered PaymentSystemCall");
        String paymentType = (String) execution.getVariable("paymentType");
            Map<String, Object> variables = new HashMap<>();
        variables.put("paymentType",paymentType);
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("makepaymentMessage")
                    .setVariables(variables)
                    .correlate();
            logger.info("makepaymentMessage Message sent");
    }
}
