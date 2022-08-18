package com.incedo.workflow.controller;

import com.incedo.workflow.model.Order;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
//
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
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("order", order);
        Random random = new Random();
        this.runtimeService.correlateMessage("orderMessage", String.valueOf(Math.abs(random.nextInt())), orderMap);
        return new ResponseEntity<>("Pizza Processing BPM is Running.", HttpStatus.OK);
    }

//    @PostMapping("/getTask")
//    public ResponseEntity<String> getTask(){
//        List<Task> taskList = this.taskService.createTaskQuery().taskUnassigned().list();
//        String taskName = null;
//        for(Task task:taskList){
//            this.taskService.complete(task.getId());
//            taskName = task.getName();
//        }
//        return new ResponseEntity<>( "Task " + taskName +" is completed.", HttpStatus.OK);
//    }

}
