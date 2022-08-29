package com.incedo.workflow.util;

import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.Pizza;
import com.incedo.workflow.model.TransactionInfo;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("PaymentPreprocessing")
public class PaymentPreprocessing implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Entered PaymentPreprocessing");
        Order order = (Order) execution.getVariable("order");
       TransactionInfo transactionInfo = order.getTransactionInfo();
            Map<String, Object> variables = new HashMap<>();
        variables.put("transactionInfo", transactionInfo);
        variables.put("paymentType",transactionInfo.getPaymentType());
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("makepaymentMessage")
                    .setVariables(variables)
                    .correlate();
            logger.info("makepaymentMessage Message sent");
    }
}
