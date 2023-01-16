package com.incedo.workflow.controller;

import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.ProcessDefinitionModel;
import com.incedo.workflow.model.CamundaVariables;
import com.incedo.workflow.model.ListOfTask;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    
    @CrossOrigin
    @GetMapping("/custom/decisiondefinition")
    public ResponseEntity<List<ProcessDefinitionModel>> getAllDecisionDefinitions() {
        List<DecisionDefinition> prodentity = processEngineService.getRepositoryService().createDecisionDefinitionQuery().list();
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
    @CrossOrigin
    @GetMapping("/custom/usertask")
    public ResponseEntity<List<ListOfTask>> getUserTasks() {
       List<Task> tasks =taskService.createTaskQuery().taskAssignee("hari").list();
  	   List<ListOfTask> listOfTask=tasks.stream().map(value -> {
  	      ListOfTask lot = new ListOfTask(); 		 
    	  List<CamundaVariables> listOfVariables = taskService.getVariables(value.getId()).keySet().stream().map(value1->{
  	          CamundaVariables camundaVar = new CamundaVariables();
  		      camundaVar.setCamundaVariableName(value1);
  		      if(taskService.getVariable(value.getId(), value1)!=null)
  			     camundaVar.setCamundaVariableValue(taskService.getVariable(value.getId(), value1).toString().toString());
  	          return camundaVar;
  		  }).collect(Collectors.toList());
  	   lot.setCamundaVariable(listOfVariables);       
  	   lot.setTaskTitle(value.getName());
  	   lot.setTaskDef(value.getTaskDefinitionKey());
  	   lot.setPriority(value.getPriority());
  	   lot.setTaskCreatedTime(value.getCreateTime());
  	   lot.setDueDate(value.getDueDate());
  	   lot.setOwnerName(value.getOwner());
  	   lot.setCheckSelect(false);
       return lot;
      }).collect(Collectors.toList());
      return ResponseEntity.ok(listOfTask);
    }
    
    @CrossOrigin
    @PostMapping(path="/custom/completetask", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCompleteTask(@RequestParam("approvalStatus") String approvalStatus,@Valid @RequestBody List<ListOfTask> tsk) {
    	String  responseMessage="";
       String responseMsg="";
    	try {
    		tsk.forEach(tskValue->{
    	   List<Task> tasks =taskService.createTaskQuery().taskDefinitionKey(tskValue.getTaskDef()).taskCreatedOn(tskValue.getTaskCreatedTime()).list();
    	   tasks.forEach(value->{
    	   if(approvalStatus.equals("approved")) 
    	   taskService.setVariable(value.getId(), "Approval", "approved");
    	   else
    	   taskService.setVariable(value.getId(), "Approval", "rejected");
    	   taskService.complete(value.getId());
    	   });
    		});
    	}catch (Exception e) {
    		responseMsg= e.getMessage();
    	}
    	if(responseMsg=="")
    		responseMsg="Selected tasks "+approvalStatus+" successfully";
    	responseMessage="{\"postStatus\""+":\""+responseMsg+"\"}";
  	 return  responseMessage;
    }
    
}
