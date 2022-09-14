package com.incedo.workflow.util;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("UpdatePaymentStatus")
public class UpdatePaymentStatus implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Entered UpdatePaymentStatus");
        Boolean sufficientBalance = (Boolean) execution.getVariable("sufficientBalance");
        log.info("sufficientBalance " + sufficientBalance + " payment successful");
        log.info(">>>>>>>>>>>>>>>>>>" + execution.getProcessInstance().getProcessBusinessKey());
        Map<String, Object> variables = new HashMap<>();
        variables.put("sufficientBalance", sufficientBalance);
        execution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("paymentcompletionmessage")
                .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                .setVariables(variables)
                .correlate();
        log.info("payment completion Message sent");
    }
}
