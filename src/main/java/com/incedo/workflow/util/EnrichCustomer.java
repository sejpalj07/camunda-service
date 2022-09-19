package com.incedo.workflow.util;

import com.incedo.workflow.model.CustomerInfo;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component("EnrichCustomer")
public class EnrichCustomer implements JavaDelegate {

    RestTemplate restTemplate;

    public EnrichCustomer() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Entered EnrichCustomer");

//        CustomerInfo customerInfo = (CustomerInfo) execution.getVariable("customerInfo");
//        customerInfo.setName("person1");
//        customerInfo.setAlternatePhoneNumber("9876543210");
//        execution.setVariable("order", customerInfo);

        ResponseEntity<CustomerInfo> customerInfoResponseEntity = restTemplate.getForEntity("https://run.mocky.io/v3/82c9794a-b96b-4a8c-9999-01df4e19d506",CustomerInfo.class);
        CustomerInfo customerInfo = customerInfoResponseEntity.getBody();
        execution.setVariable("customerInfo",customerInfo);
        log.info("received customer info from api : " + customerInfo);
    }

}