package com.incedo.workflow.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ValidateAccountBalance")
public class ValidateAccountBalance implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("Entered ValidateAccountBalance");
//        TransactionInfo transactionInfo = (TransactionInfo) execution.getVariable("transactionInfo");
//        Long accountBalance = transactionInfo.getBalance();
//        Long billPrice = transactionInfo.getBillPrice();
//        if(billPrice<= accountBalance){
//            execution.setVariable("sufficientBalance", true);
//        }else {
//            execution.setVariable("sufficientBalance", false);
//        }
        execution.setVariable("sufficientBalance", true);
    }

}
