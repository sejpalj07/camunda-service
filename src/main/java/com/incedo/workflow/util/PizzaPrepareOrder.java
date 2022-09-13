package com.incedo.workflow.util;

import com.incedo.workflow.exception.BPMNErrorList;
import com.incedo.workflow.model.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("PizzaPrepareOrder")
public class PizzaPrepareOrder implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("PizzaPrepareOrder start");
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
            log.error(BPMNErrorList.ERROR_MESSAGE_NOT_CORRELATE + msg + "with Business key: " + execution.getProcessBusinessKey());
//            throw new ListEmptyException(BPMNErrorList.ERROR_MESSAGE_NOT_CORRELATE, msg);
        }
    }
}
