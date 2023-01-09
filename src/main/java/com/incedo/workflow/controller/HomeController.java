package com.incedo.workflow.controller;

import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.ProcessDefinitionModel;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class HomeController {
    private final RuntimeService runtimeService;
    private final ProcessEngine processEngineService;
    private final TaskService taskService;
    private final Random random = new Random();

    public HomeController(@Autowired RuntimeService runtimeService, @Autowired TaskService taskService, @Autowired ProcessEngine processEngineService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.processEngineService = processEngineService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> invokeProcess(@Valid @RequestBody Order order) {
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("pizzaList", order.getPizzaList());
        orderMap.put("sideList", order.getSideList());
        orderMap.put("drinksList", order.getDrinksList());
        orderMap.put("paymentType", order.getPaymentType());
        orderMap.put("deliveryType", order.getDeliveryType());
        orderMap.put("pickupTime", order.getPickupTime());
        orderMap.put("address", order.getAddress());
        orderMap.put("validationMessage", order.getValidationMessage());
        orderMap.put("customerInfo", order.getCustomerInfo());
        String bKey = String.valueOf(Math.abs(random.nextInt()));
        this.runtimeService.correlateMessage("orderMessage", bKey, orderMap);
        return new ResponseEntity<>("Pizza Processing BPM is Running.", HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/custom/processdefination")
    public ResponseEntity<List<ProcessDefinitionModel>> getAllProcessDefinitions() {
        List<ProcessDefinition> prodentity = processEngineService.getRepositoryService().createProcessDefinitionQuery().list();
        List<ProcessDefinitionModel> sampleName = prodentity.stream().map(value -> {
            ProcessDefinitionModel pdm = new ProcessDefinitionModel();
            pdm.setDefinitionId(value.getId());
            pdm.setName(value.getName());
            pdm.setDefinitionKey(value.getKey());
            pdm.setVersion(value.getVersion());
            pdm.setVersionTag(value.getVersionTag());
            return pdm;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(sampleName);
    }
}
