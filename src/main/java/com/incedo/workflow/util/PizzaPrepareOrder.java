package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.exception.ListEmptyException;
import com.incedo.workflow.model.Pizza;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("PizzaPrepareOrder")
public class PizzaPrepareOrder implements JavaDelegate {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("PizzaPrepareOrder start");
        Pizza pizza = (Pizza) execution.getVariable("eachPizza");
        Map<String, Object> pizzaName = new HashMap<>();
        pizzaName.put("pizza", pizza);
        try {
            execution.getProcessEngineServices()
                    .getRuntimeService()
                    .createMessageCorrelation("PizzaCreationMessage")
                    .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                    .setVariables(pizzaName)
                    .correlate();
        } catch (Exception ex) {
            String msg = "Can't Correlate " + pizza + "because of " + ex.getMessage();
            logger.error(BPMNErrorList.ERROR_CORRELATION_PIZZA + msg + "with Business key: " + execution.getProcessBusinessKey());
            throw new ListEmptyException(BPMNErrorList.ERROR_CORRELATION_PIZZA, msg);
        }
    }
}
