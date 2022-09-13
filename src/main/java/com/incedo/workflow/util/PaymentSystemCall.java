package com.incedo.workflow.util;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("PaymentSystemCall")
public class PaymentSystemCall implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Entered PaymentSystemCall");
        String paymentType = (String) execution.getVariable("paymentType");
        Map<String, Object> variables = new HashMap<>();
        variables.put("paymentType", paymentType);
        execution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("makepaymentMessage")
                .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                .setVariables(variables)
                .correlate();
        log.info("makepaymentMessage Message sent");
    }
}
