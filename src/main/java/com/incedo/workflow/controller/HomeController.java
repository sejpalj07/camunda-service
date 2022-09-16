package com.incedo.workflow.controller;

import com.incedo.workflow.model.Order;
import com.incedo.workflow.service.OrderService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Random;

@RestController
public class HomeController {
    private RuntimeService runtimeService;
    private TaskService taskService;
    private OrderService orderService;

    public String getKey() {
        Random random = new Random();
        String bKey = String.valueOf(Math.abs(random.nextInt()));
        return bKey;
    }
    public HomeController(@Autowired RuntimeService runtimeService,
                          @Autowired TaskService taskService,
                          @Autowired OrderService orderService){
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.orderService = orderService;
    }


    @PostMapping("/process")
    public ResponseEntity<String> invokeProcess(@RequestBody @Valid Order order){
        this.runtimeService.correlateMessage("orderMessage", getKey() ,
                orderService.getOrder(order));
        return new ResponseEntity<>("Pizza Processing BPM is Running.", HttpStatus.OK);
    }
}
