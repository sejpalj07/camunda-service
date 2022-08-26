package com.incedo.workflow.controller;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Order;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/exeBPM")
//    public ResponseEntity<String> getTest(){
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-project", "12");
//        return new ResponseEntity<>("User BPM is Running:"+processInstance.getId(), HttpStatus.OK);
//    }

//    @GetMapping("/getAllTask/{processId}")
//    public List<String> getAllTask(@PathVariable String processId){
//        List<String> idList = runtimeService.getActiveActivityIds(processId);
//        System.out.println(idList);
//        return idList;
//    }
//
//    @PostMapping("/completeTask")
//    public ResponseEntity<String> completeTask(@RequestBody String data){
//        List<Task> taskList = this.taskService.createTaskQuery().taskUnassigned().list();
//        JsonElement jsonElement=JsonParser.parseString(data);
//        JsonObject jsonObject= jsonElement.getAsJsonObject();
//        Map<String, Object> variables = Map.of("role",jsonObject.get("role").getAsString());
//        for(Task task:taskList){
//            this.taskService.complete(task.getId(), variables);
//            System.out.println(task.getName());
//        }
//        return new ResponseEntity<>("Task completed", HttpStatus.OK);
//    }

    @PostMapping("/process")
    public ResponseEntity<String> invokeProcess(@RequestBody Order order){
        List<Item> ItemList = new ArrayList<>();
        Map<String, Object> orderMap = new HashMap<>();
//        orderMap.put("order", order);
        orderMap.put("pizzaList", order.getPizzaList());
        orderMap.put("sideList", order.getSideList());
        orderMap.put("drinksList", order.getDrinksList());
        orderMap.put("ItemList", ItemList);
        Random random = new Random();
        String bKey = String.valueOf(Math.abs(random.nextInt()));
        this.runtimeService.correlateMessage("orderMessage", bKey, orderMap);
        return new ResponseEntity<>("Pizza Processing BPM is Running.", HttpStatus.OK);
    }

}
