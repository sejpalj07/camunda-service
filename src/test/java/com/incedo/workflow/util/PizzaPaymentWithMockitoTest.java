package com.incedo.workflow.util;


import com.incedo.workflow.model.*;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.act.MessageIntermediateCatchEventAction;
import org.camunda.bpm.scenario.act.ServiceTaskAction;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/*
References:
1. https://camunda.com/blog/2020/10/testing-entire-process-paths/
2. https://github.com/FlowSquad/camunda-testing-examples/blob/master/src/main/resources/deliver-process.bpmn
3. https://github.com/FlowSquad/camunda-testing-examples/blob/master/src/test/java/io/flowsquad/camunda/test/WorkflowTest.java
4. https://github.com/camunda-community-hub/camunda-platform-7-mockito/blob/master/src/test/java/org/camunda/bpm/extension/mockito/CallActivityMockExampleTest.java
 */

@Deployment(resources = {"PizzaOrderingSystem.bpmn","assistantChef.bpmn", "ValidateDrinks.dmn", "cal-discount.dmn", "paymentSystem.bpmn", "pizzaChef.bpmn", "PriceCalculator.dmn"})
public class PizzaPaymentWithMockitoTest {
    public static final String PriceCalculatorDecision = "PriceCalculatorDecision";
    public static final String ValidateDrinksDecision = "ValidateDrinksDecision";

    @Rule
    @ClassRule
    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
            .excludeProcessDefinitionKeys(PriceCalculatorDecision)
            .excludeProcessDefinitionKeys(ValidateDrinksDecision)
            .assertClassCoverageAtLeast(0.7)
            .build();

    private SidePrepareOrder sidePrepareOrder;
    private PizzaPrepareOrder pizzaPrepareOrder;

    @Mock
    private ProcessScenario pizzaOrderingSystem;

    @Mock
    private ProcessScenario AssistantChef;

    @Mock
    private ProcessScenario PizzaChef;

    private EnrichCustomer enrichCustomer;

    @Before
    public void defaultScenario() {
        MockitoAnnotations.initMocks(this);
        Mocks.register("ValidatePizzaDelegate", new ValidatePizzaDelegate());
        Mocks.register("PizzaPrepareOrder", mock(PizzaPrepareOrder.class));
        Mocks.register("PizzaDelegate", new PizzaDelegate());
        Mocks.register("PizzaStatusDelegate", mock(PizzaStatusDelegate.class));
        Mocks.register("CheckPizzaStatusFromKitchen", new CheckPizzaStatusFromKitchen());

        Mocks.register("ValidateSideDelegate", new ValidateSideDelegate());
        Mocks.register("SidePrepareOrder", mock(SidePrepareOrder.class));
        Mocks.register("SideDelegate", new SideDelegate());
        Mocks.register("SideStatusDelegate", mock(SideStatusDelegate.class));
        Mocks.register("CheckSideStatusFromKitchen", new CheckSideStatusFromKitchen());

        Mocks.register("CombineList", new CombineList());
        Mocks.register("AddItemListPrice", new AddItemListPrice());
        Mocks.register("FindTotalDelegate", new FindTotalDelegate());

        Mocks.register("EnrichCustomer", new EnrichCustomer());
        Mocks.register("PaymentSystemCall", mock(PaymentSystemCall.class));
        Mocks.register("PickupOrder", new PickupOrder());
        Mocks.register("HomeDelivery", new HomeDelivery());
        Mocks.register("DeliverCompleted", new DeliverCompleted());
        Mocks.register("ErrorLog", new ErrorLog());

        enrichCustomer = mock(EnrichCustomer.class);
        Mocks.register("EnrichCustomer",enrichCustomer);
        when(pizzaOrderingSystem.waitsAtMessageIntermediateCatchEvent("MultiInstance_Pizza_RecievePizzaChef"))
                .thenReturn(task -> {
                    task.receive();
                });
        when(pizzaOrderingSystem.waitsAtMessageIntermediateCatchEvent("MultiInstance_Side_CallActivity"))
                .thenReturn(task -> {
                    task.receive();
                });
        when(pizzaOrderingSystem.waitsAtEventBasedGateway("InclusiveGateway_AfterValidation"))
                .thenReturn(event -> {
                    event.getEventSubscription().receive();
                });
        when(pizzaOrderingSystem.waitsAtUserTask("UT_Side"))
                .thenReturn(task -> {
                    task.complete();
                });
    }

    @Test
    public void PizzaOrderingSystemBasicTest() throws Exception {
        String bKey = "bKey";
        Map<String, Object> variables = foodOrder();
//        when(pizzaOrderingSystem.runsCallActivity("MultiInstance_Side_CallActivity"))
//                .thenReturn(Scenario.use(AssistantChef));
//
//        when(pizzaOrderingSystem.waitsAtEventBasedGateway("EventBasedGateway_WaitForPayment")).thenReturn(gateway -> {
//            gateway.getEventSubscription().receive();
//        });
//        when(pizzaOrderingSystem.waitsAtMessageIntermediateCatchEvent("MultiInstance_Pizza_RecievePizzaChef"))
//                .thenReturn(Mockito.mock(MessageIntermediateCatchEventAction.class));
        Scenario.run(pizzaOrderingSystem)
                .startByMessage("orderMessage", variables)
                .execute();
        verify(pizzaOrderingSystem).hasCompleted("JD_pizza-validation");
        verify(pizzaOrderingSystem).hasCompleted("JD_side-validation");

    }

//    @Test
//    public void ItemNotValidTest() throws Exception {
//        Map<String, Object> variables = foodOrder();
//        when(pizzaOrderingSystem.waitsAtMessageIntermediateCatchEvent("MultiInstance_Pizza_RecievePizzaChef"))
//                .thenReturn(Mockito.mock(MessageIntermediateCatchEventAction.class));
//        Scenario.run(pizzaOrderingSystem)
//                .startByMessage("orderMessage", variables)
//                .execute();
//        verify(pizzaOrderingSystem).hasCompleted("JD_pizza-validation");
//        verify(pizzaOrderingSystem).hasCompleted("JD_side-validation");
//    }

    public Map<String, Object> foodOrder() {
        Order order = new Order();
        List<Item> ItemList = new ArrayList<>();

        List<Pizza> pizzaList = new ArrayList<>();
        List<String> toppings = new ArrayList<>(Arrays.asList("greenpapper", "mushroom"));
        pizzaList.add(new Pizza("12", "Veggie Pizza", toppings));
        order.setPizzaList(pizzaList);

        List<Side> sideList = new ArrayList<>();
        sideList.add(new Side("12", "Garlic Bread"));
        order.setSideList(sideList);

        List<String> drinkList = new ArrayList<>(Arrays.asList("Water", "Soda"));
        order.setDrinksList(drinkList);

        order.setPaymentType("online");
        order.setDeliveryType("pickup");
        order.setAddress("1002 Fuller Wiser Rd.");
        order.setValidationMessage("");

        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setPhoneNumber("123456789");
        order.setCustomerInfo(customerInfo);

        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("pizzaList", order.getPizzaList());
        orderMap.put("sideList", order.getSideList());
        orderMap.put("drinksList", order.getDrinksList());
        orderMap.put("ItemList", ItemList);
        orderMap.put("paymentType", order.getPaymentType());
        orderMap.put("deliveryType", order.getDeliveryType());
        orderMap.put("pickupTime", order.getPickupTime());
        orderMap.put("address", order.getAddress());
        orderMap.put("validationMessage", order.getValidationMessage());
        orderMap.put("customerInfo", order.getCustomerInfo());
        return orderMap;
    }

}