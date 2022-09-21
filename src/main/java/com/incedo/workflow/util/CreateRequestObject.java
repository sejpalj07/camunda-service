package com.incedo.workflow.util;

import com.incedo.workflow.model.RequestType;
import com.incedo.workflow.model.RequestModel;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("CreateRequestObject")
@Slf4j
public class CreateRequestObject implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Entered CreateRequestObject");
        RequestModel requestModel = new RequestModel();
        requestModel.setHttpRequestUrl("https://run.mocky.io/v3/82c9794a-b96b-4a8c-9999-01df4e19d506");
        requestModel.setHttpRquestType(RequestType.GET);
        execution.setVariable("requestModel",requestModel);
        log.info("Completed CreateRequestObject");
    }
}
