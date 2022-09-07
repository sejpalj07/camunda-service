package com.incedo.workflow.controller;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Order;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class HomeController {
    RuntimeService runtimeService;
    TaskService taskService;
    public HomeController(@Autowired RuntimeService runtimeService,
                          @Autowired TaskService taskService){
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }
    @PostMapping("/process")
    public ResponseEntity<String> invokeProcess(@RequestBody Order order){
        List<Item> ItemList = new ArrayList<>();
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("pizzaList", order.getPizzaList());
        orderMap.put("sideList", order.getSideList());
        orderMap.put("drinksList", order.getDrinksList());
        orderMap.put("ItemList", ItemList);
        orderMap.put("paymentType",order.getPaymentType());
        orderMap.put("deliveryType",order.getDeliveryType());
        orderMap.put("pickupTime",order.getPickupTime());
        orderMap.put("address",order.getAddress());
        orderMap.put("validationMessage",order.getValidationMessage());
        orderMap.put("customerInfo",order.getCustomerInfo());
        Random random = new Random();
        String bKey = String.valueOf(Math.abs(random.nextInt()));

        this.runtimeService.correlateMessage("orderMessage", bKey, orderMap);
        return new ResponseEntity<>("Pizza Processing BPM is Running.", HttpStatus.OK);
    }

}
