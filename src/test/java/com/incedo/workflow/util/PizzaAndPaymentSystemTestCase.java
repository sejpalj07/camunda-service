//package com.incedo.workflow.util;
//
//import com.incedo.workflow.model.*;
//import org.camunda.bpm.engine.ManagementService;
//import org.camunda.bpm.engine.ProcessEngine;
//import org.camunda.bpm.engine.RuntimeService;
//import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
//import org.camunda.bpm.engine.runtime.ActivityInstance;
//import org.camunda.bpm.engine.runtime.Job;
//import org.camunda.bpm.engine.runtime.ProcessInstance;
//import org.camunda.bpm.engine.task.Task;
//import org.camunda.bpm.engine.task.TaskQuery;
//import org.camunda.bpm.engine.test.Deployment;
//import org.camunda.bpm.engine.test.ProcessEngineRule;
//import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.PostConstruct;
//import java.util.*;
//
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
//import static org.junit.Assert.assertEquals;
//
////@RunWith(MockitoJUnitRunner.class)
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class PizzaAndPaymentSystemTestCase {
//    private static final String PROCESS_KEY = "PizzaOrderingSystemWithPaymentSystem";
//
//    @Rule
//    public ProcessEngineRule rule = new ProcessEngineRule();
//
////    @Rule
////    @ClassRule
////    public static ProcessEngineRule rule;
//
//    //    @Autowired
//    ProcessEngine processEngine; // getting as null
//
//    @Autowired
//    RuntimeService runtimeService; // getting as null
//
////    @PostConstruct
////    void initRule() {
////        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
////    }
//
//    @Before
//    public void initRule() {
//        processEngine = rule.getProcessEngine();
//        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
////        processEngine = rule.getProcessEngine().getProcessEngineConfiguration().buildProcessEngine();
//
////        runtimeService = rule.getRuntimeService();
//    }
//
//    @Test
//    @Deployment(resources = {"PizzaOrderingSystem-withPaymentSystem.bpmn"})
//    public void testSampleCase_happyPath() {
//
//        Order order = new Order();
//        List<Item> ItemList = new ArrayList<>();
//
//        List<Pizza> pizzaList = new ArrayList<>();
//        List<String> toppings = new ArrayList<>();
//        toppings.add("greenpapper");
//        toppings.add("mushroom");
//        pizzaList.add(new Pizza("12", "Veggie Pizza", toppings));
//        order.setPizzaList(pizzaList);
//
//        List<Side> sideList = new ArrayList<>();
//        sideList.add(new Side("12", "Garlic Bread"));
//        order.setSideList(sideList);
//
//        List<String> drinkList = new ArrayList<>();
//        drinkList.add("Water");
//        order.setDrinksList(drinkList);
//
//        order.setPaymentType("online");
//        order.setDeliveryType("pickup");
//        //order.setPickupTime("2018-03-29T13:34:00.000");
//        order.setAddress("");
//        order.setValidationMessage("");
//
//        CustomerInfo customerInfo = new CustomerInfo();
//        customerInfo.setPhonenumber("123456789");
//        order.setCustomerInfo(customerInfo);
//
//
//        Map<String, Object> orderMap = new HashMap<>();
//
//        orderMap.put("pizzaList", order.getPizzaList());
//        orderMap.put("sideList", order.getSideList());
//        orderMap.put("drinksList", order.getDrinksList());
//        orderMap.put("ItemList", ItemList);
//        orderMap.put("paymentType", order.getPaymentType());
//        orderMap.put("deliveryType", order.getDeliveryType());
//        orderMap.put("pickupTime", order.getPickupTime());
//        orderMap.put("address", order.getAddress());
//        orderMap.put("validationMessage", order.getValidationMessage());
//        orderMap.put("customerInfo", order.getCustomerInfo());
//        Random random = new Random();
//        String bKey = String.valueOf(Math.abs(random.nextInt()));
//
//
//        ProcessInstance instance = runtimeService().startProcessInstanceByMessage("orderMessage", bKey, orderMap);
//        // referring - https://github.com/camunda/camunda-bpm-platform/blob/master/engine/src/test/java/org/camunda/bpm/engine/test/bpmn/subprocess/SubProcessTest.java
////        ActivityInstance rootActivityInstance = runtimeService.getActivityInstance(instance.getProcessInstanceId());
////        assertEquals(instance.getProcessDefinitionId(), rootActivityInstance.getActivityId());
//
////        java.lang.AssertionError: Expecting ProcessInstance {id='fdef5c70-2ea4-11ed-b691-50e085e44b12', processDefinitionId='fde0b662-2ea4-11ed-b691-50e085e44b12', businessKey='1139077459'} to be waiting at exactly [side-validation], but it is actually waiting at [side-validation, customerenrichsignalthrow, Activity_0golmdj, pizza-validation].
//        assertThat(instance)
//                .isActive();
//        //.hasPassed("received-order")
//        //.hasPassed("Gateway_0i8ie1t")
//        //.hasPassed("Gateway_0knz79p")
//        //.hasPassed("customerenrichsignalthrow")
//        //.isWaitingAt("side-validation", "Activity_0golmdj", "pizza-validation");
//
////                .isWaitingAtExactly("customerenrichsignalthrow")
////                .isWaitingAtExactly("Activity_0golmdj")
////                .isWaitingAtExactly("pizza-validation");
////                .task().isNotAssigned();
////        executeAsyncTask("pizza-validation");
////        executeAsyncTask("side-validation");
////        executeAsyncTask("Activity_0golmdj");//Validate Drinks
//
////        Task task = processEngine.getTaskService().createTaskQuery() .active() .taskName("side-validation").singleResult();
//        try {
//            Thread.sleep(10000); //sleeping for 10sec
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        assertThat(instance)
//                .isActive();
//        // referred from - https://forum.camunda.io/t/getting-the-current-task/6482
//        List<Task> tasks = processEngine.getTaskService().createTaskQuery().active()
////                .taskName("side-validation")
//                .processInstanceId(instance.getId()).list();
//        // tasks size = 0
//        for (Task task : tasks) {
//            //taskService.complete(task.getId())
//        }
//
//        // At this point > 0 tasks but 4 concurrent job executions are available
//
//        // validating variable value
//        assertEquals(runtimeService.getVariables("validationMessage"), "");
//
//
////Getting all child executions
////        ((ExecutionEntity) instance).getAllChildExecutions();  - this is showing executions in debugger but in java code
//
//        //concurrent processes
////        assertEquals(1, rootActivityInstance.getChildActivityInstances().length);
////        ActivityInstance subProcessInstance = rootActivityInstance.getChildActivityInstances()[0];
////        assertEquals("subProcess", subProcessInstance.getActivityId());
//
////        complete(task(),withVariables( // task getting as null - ???
////                "assignPerson", "sumanth",
////                "attribute1", "value1"
////        ));
////
////        assertThat(instance)
////                .isActive()
////                .isWaitingAt("Activity_0golmdj","pizza-validation");
////
////        complete(task(),withVariables(
////                "assignPerson", "sumanth",
////                "attribute1", "value1"
////        )); // task getting as null - ???
////
////        assertThat(instance)
////                .isActive()
////                .isWaitingAt("pizza-validation");
////
////        complete(task(),withVariables(
////                "assignPerson", "sumanth",
////                "attribute1", "value1"
////        )); // task getting as null - ???
//    }
////    //referred the camunda forum - https://forum.camunda.io/t/behaviour-inclusive-gateway-in-unittest-vs-jobexecutor/1171/8 - not using this instead using completeTask()
////    private void executeAsyncTask(String activityId){
////        ManagementService managementService = processEngine.getManagementService();
////        Job job = managementService.createJobQuery().active().activityId(activityId).singleResult();
////        managementService.executeJob(job.getId());
////    }
//}
