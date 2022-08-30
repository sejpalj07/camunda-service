package com.incedo.workflow.util;

import com.incedo.workflow.model.CustomerInfo;
import com.incedo.workflow.model.PaymentMode;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("UpdatePaymentStatus")
public class UpdatePaymentStatus implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Entered UpdatePaymentStatus");
        Boolean sufficientBalance = (Boolean) execution.getVariable("sufficientBalance");
        if (sufficientBalance) {
            logger.info("sufficientBalance " + sufficientBalance + " payment successful");
            Map<String, Object> variables = new HashMap<>();
            variables.put("sufficientBalance", sufficientBalance);
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("paymentcompletionmessage")
                    .setVariables(variables)
                    .correlate();
            logger.info("paymentcompletionmessage Message sent");
        } else {

            logger.info("sufficientBalance " + sufficientBalance + " payment failure");
            Map<String, Object> variables = new HashMap<>();
            variables.put("sufficientBalance", sufficientBalance);
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("paymentProcessFailureMessage")
                    .setVariables(variables)
                    .correlate();
            logger.info("paymentProcessFailureMessage Message sent");
        }



    }
}
