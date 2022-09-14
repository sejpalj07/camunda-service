package com.incedo.workflow.util;


import com.incedo.workflow.model.*;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.act.MessageIntermediateCatchEventAction;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/*
References:
1. https://camunda.com/blog/2020/10/testing-entire-process-paths/
2. https://github.com/FlowSquad/camunda-testing-examples/blob/master/src/main/resources/deliver-process.bpmn
3. https://github.com/FlowSquad/camunda-testing-examples/blob/master/src/test/java/io/flowsquad/camunda/test/WorkflowTest.java
4. https://github.com/camunda-community-hub/camunda-platform-7-mockito/blob/master/src/test/java/org/camunda/bpm/extension/mockito/CallActivityMockExampleTest.java
 */

@Deployment(resources = {"PizzaOrderingSystem.bpmn"})
public class PizzaPaymentWithMockitoTest {
    public static final String PROCESS_KEY = "PizzaOrderingSystemWithPaymentSystem";
    public static final String TASK_DELIVER_ORDER = "Task_DeliverOrder";
    public static final String VAR_ORDER_DELIVERED = "orderDelivered";
    public static final String PizzaPriceCalculatorDecision = "PizzaPriceCalculatorDecision";
    public static final String SidePriceCalculatorDecision = "SidePriceCalculatorDecision";
    public static final String DrinksPriceCalculatorDecision = "DrinksPriceCalculatorDecision";
    public static final String priceCalculator = "priceCalculator";

    @Rule
    @ClassRule
    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
            .excludeProcessDefinitionKeys(PizzaPriceCalculatorDecision)
            .excludeProcessDefinitionKeys(SidePriceCalculatorDecision)
            .excludeProcessDefinitionKeys(DrinksPriceCalculatorDecision)
            .excludeProcessDefinitionKeys(priceCalculator)
            .assertClassCoverageAtLeast(0.252)
            .build();

    @Mock
    private ProcessScenario pizzaOrderingSystem;

    SidePrepareOrder sidePrepareOrder;
    PizzaPrepareOrder pizzaPrepareOrder;

    @Before
    public void defaultScenario() {
        MockitoAnnotations.initMocks(this);

        Mocks.register("ValidatePizzaDelegate", new ValidatePizzaDelegate());
        Mocks.register("ValidateSideDelegate", new ValidateSideDelegate());
        Mocks.register("ValidateDrinksDelegate", new ValidateDrinksDelegate());
        sidePrepareOrder = Mockito.mock(SidePrepareOrder.class);
        Mocks.register("SidePrepareOrder", sidePrepareOrder);
        pizzaPrepareOrder = Mockito.mock(PizzaPrepareOrder.class);
        Mocks.register("PizzaPrepareOrder", pizzaPrepareOrder);
        Mocks.register("CheckPizzaStatusFromKitchen", Mockito.mock(CheckPizzaStatusFromKitchen.class));

    }

    @Test
    public void shouldExecuteHappyPath() throws Exception {
        String bKey = "bKey";
        Map<String, Object> variables = foodOrder();


        doNothing().when(sidePrepareOrder).execute(any());
        when(pizzaOrderingSystem.waitsAtMessageIntermediateCatchEvent("Event_0xa3wii"))
                .thenReturn(Mockito.mock(MessageIntermediateCatchEventAction.class));
        when(pizzaOrderingSystem.waitsAtMessageIntermediateCatchEvent("Event_0hqdn4x"))
                .thenReturn(Mockito.mock(MessageIntermediateCatchEventAction.class));


        Scenario.run(pizzaOrderingSystem)
                .startByMessage("orderMessage", variables)
                .execute();

        verify(pizzaOrderingSystem).hasCompleted("side-validation");
        verify(pizzaOrderingSystem).hasCompleted("Activity_0golmdj");
        verify(pizzaOrderingSystem).hasCompleted("pizza-validation");
    }

    public Map<String, Object> foodOrder() {
        Order order = new Order();
        List<Item> ItemList = new ArrayList<>();
        List<Pizza> pizzaList = new ArrayList<>();
        List<String> toppings = new ArrayList<>();
        toppings.add("greenpapper");
        toppings.add("mushroom");
        pizzaList.add(new Pizza("12", "Veggie Pizza", toppings));
        order.setPizzaList(pizzaList);
        List<Side> sideList = new ArrayList<>();
        sideList.add(new Side("12", "Garlic Bread"));
        order.setSideList(sideList);
        List<String> drinkList = new ArrayList<>();
        drinkList.add("Water");
        order.setDrinksList(drinkList);
        order.setPaymentType("online");
        order.setDeliveryType("pickup"); // order.setPickupTime("2018-03-29T13:34:00.000");
        order.setAddress("");
        order.setValidationMessage("");
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setPhonenumber("123456789");
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