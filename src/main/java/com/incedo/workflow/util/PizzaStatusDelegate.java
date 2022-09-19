package com.incedo.workflow.util;

import com.incedo.workflow.model.Pizza;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component("PizzaStatusDelegate")
public class PizzaStatusDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("PizzaStatusDelegate start");
        Pizza pizza = (Pizza) execution.getVariable("pizza");
        Map<String, Object> pizzaStatus = new HashMap<>();
        pizzaStatus.put("completedPizza", pizza);
        execution.getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("PizzaStatusMessage")
                .processInstanceBusinessKey(execution.getProcessInstance().getProcessBusinessKey())
                .setVariables(pizzaStatus)
                .correlate();
        log.info("PizzaStatusDelegate end");
    }
}
