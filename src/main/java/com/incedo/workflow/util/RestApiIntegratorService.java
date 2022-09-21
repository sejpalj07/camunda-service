package com.incedo.workflow.util;

import com.incedo.workflow.model.RequestType;
import com.incedo.workflow.model.RequestModel;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("RestApiIntegratorService")
@Slf4j
public class RestApiIntegratorService implements JavaDelegate {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Entered RestApiIntegratorService");
        RequestModel requestModel = (RequestModel)delegateExecution.getVariable("requestModel");
        String correlationId = requestModel.getCorrelationId();
        RequestType httpRquestType = requestModel.getHttpRquestType();
        String httpRequestUrl = requestModel.getHttpRequestUrl();
        Map<String, String> httpRequestHeader = requestModel.getHttpRequestHeader();

        ResponseEntity<String> responseEntity = null;

        if (httpRquestType == RequestType.GET) {

            responseEntity = restTemplate.getForEntity(httpRequestUrl, String.class);

        } else if (httpRquestType == RequestType.POST) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            for (String key : httpRequestHeader.keySet()) {
                headers.add(key, httpRequestHeader.get("key"));
            }

            HttpEntity<String> request =
                    new HttpEntity<String>((String) delegateExecution.getVariable("requestObject"), headers); // where to get body from?

            responseEntity = restTemplate.postForEntity(httpRequestUrl, request, String.class);

        } else if (httpRquestType == RequestType.PUT) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            for (String key : httpRequestHeader.keySet()) {
                headers.add(key, httpRequestHeader.get("key"));
            }

            HttpEntity<String> request =
                    new HttpEntity<String>((String) delegateExecution.getVariable("requestObject"), headers);

            restTemplate.put(httpRequestUrl, request, String.class); // void method
        }

        requestModel.setResponseEntity(responseEntity);
        delegateExecution.setVariableLocal("requestModel", requestModel); //updated the same variable again
        log.info("Completed RestApiIntegratorService");

    }
}
