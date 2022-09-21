package com.incedo.workflow.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.incedo.workflow.model.*;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/*
References:
1. https://camunda.com/blog/2020/10/testing-entire-process-paths/
2. https://github.com/FlowSquad/camunda-testing-examples/blob/master/src/main/resources/deliver-process.bpmn
3. https://github.com/FlowSquad/camunda-testing-examples/blob/master/src/test/java/io/flowsquad/camunda/test/WorkflowTest.java
4. https://github.com/camunda-community-hub/camunda-platform-7-mockito/blob/master/src/test/java/org/camunda/bpm/extension/mockito/CallActivityMockExampleTest.java
 */

@Deployment(resources = {"PizzaOrderingSystem.bpmn","assistantChef.bpmn", "ValidateDrinks.dmn", "cal-discount.dmn", "paymentSystem.bpmn", "PriceCalculator.dmn"})
public class PizzaPaymentWithMockitoTest {
    public static final String PriceCalculatorDecision = "PriceCalculatorDecision";
    public static final String ValidateDrinksDecision = "ValidateDrinksDecision";

    @Rule
    @ClassRule
    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
            .excludeProcessDefinitionKeys(PriceCalculatorDecision)
            .excludeProcessDefinitionKeys(ValidateDrinksDecision)
            .assertClassCoverageAtLeast(0.5)
            .build();
    @Mock
    private ProcessScenario pizzaOrderingSystem;
    @Mock
    private ProcessScenario AssistantChef;
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
        Mocks.register("CashPayment", new CashPayment());
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
        when(pizzaOrderingSystem.waitsAtUserTask("UT_Invalid_Item"))
                .thenReturn(task -> {
                    task.complete();
                });
        when(pizzaOrderingSystem.waitsAtUserTask("UT_DummyUserTask"))
                .thenReturn(task -> {
                    task.complete();
                });
        when(AssistantChef.waitsAtUserTask("UT_Side"))
                .thenReturn(task -> {
                    task.complete();
                });
    }
   @Test
    public void PizzaOrderingSystemOnlineDeliveryTest( ) throws Exception {
        String path = "src/test/resources/data/online_delivery_data.json";
       List<Map<String, Object>> variables = foodOrder(path);
       for (Map<String, Object> var:variables) {
//           Mockito.reset(pizzaOrderingSystem);
           when(pizzaOrderingSystem.runsCallActivity("MultiInstance_Side_CallActivity"))
                   .thenReturn(Scenario.use(AssistantChef));
           when(pizzaOrderingSystem.waitsAtEventBasedGateway("EventBasedGateway_WaitForPayment"))
                   .thenReturn(gateway -> {
                       gateway.getEventSubscription().receive();
                   });
           Scenario.run(pizzaOrderingSystem)
                   .startByMessage("orderMessage", var)
                   .execute();
           verify(pizzaOrderingSystem).hasCompleted("JD_pizza-validation");
           verify(pizzaOrderingSystem).hasCompleted("JD_side-validation");
       }
   }
    @Test
    public void PizzaOrderingSystemOnlinePickupTest( ) throws Exception {
        String path = "src/test/resources/data/online_pickup_data.json";
        List<Map<String, Object>> variables = foodOrder(path);
        for (Map<String, Object> var:variables) {
            when(pizzaOrderingSystem.runsCallActivity("MultiInstance_Side_CallActivity"))
                    .thenReturn(Scenario.use(AssistantChef));
            when(pizzaOrderingSystem.waitsAtEventBasedGateway("EventBasedGateway_WaitForPayment"))
                    .thenReturn(gateway -> {
                        gateway.getEventSubscription().receive();
                    });
            Scenario.run(pizzaOrderingSystem)
                    .startByMessage("orderMessage", var)
                    .execute();
            verify(pizzaOrderingSystem).hasCompleted("JD_pizza-validation");
            verify(pizzaOrderingSystem).hasCompleted("JD_side-validation");
        }
    }

    @Test
    public void PizzaOrderingSystemInvalidItemTest( ) throws Exception {
        String path = "src/test/resources/data/invalid_data.json";
        List<Map<String, Object>> variables = foodOrder(path);
        for (Map<String, Object> var:variables) {
            when(pizzaOrderingSystem.runsCallActivity("MultiInstance_Side_CallActivity"))
                    .thenReturn(Scenario.use(AssistantChef));
            when(pizzaOrderingSystem.waitsAtEventBasedGateway("EventBasedGateway_WaitForPayment"))
                    .thenReturn(gateway -> {
                        gateway.getEventSubscription().receive();
                    });
            Scenario.run(pizzaOrderingSystem)
                    .startByMessage("orderMessage", var)
                    .execute();
            verify(pizzaOrderingSystem).hasCompleted("JD_pizza-validation");
            verify(pizzaOrderingSystem).hasCompleted("JD_side-validation");
        }
    }
    @Test
    public void PizzaOrderingSystemCashDeliveryTest( ) throws Exception {
        String path = "src/test/resources/data/cash_delivery_data.json";
        List<Map<String, Object>> variables = foodOrder(path);
        for (Map<String, Object> var:variables) {
//           Mockito.reset(pizzaOrderingSystem);
            when(pizzaOrderingSystem.runsCallActivity("MultiInstance_Side_CallActivity"))
                    .thenReturn(Scenario.use(AssistantChef));
            when(pizzaOrderingSystem.waitsAtEventBasedGateway("EventBasedGateway_WaitForPayment"))
                    .thenReturn(gateway -> {
                        gateway.getEventSubscription().receive();
                    });
            Scenario.run(pizzaOrderingSystem)
                    .startByMessage("orderMessage", var)
                    .execute();
            verify(pizzaOrderingSystem).hasCompleted("JD_pizza-validation");
            verify(pizzaOrderingSystem).hasCompleted("JD_side-validation");
        }
    }
    @Test
    public void PizzaOrderingSystemPriceTest( ) throws Exception {
        String path = "src/test/resources/data/no_price_data.json";
        List<Map<String, Object>> variables = foodOrder(path);
        for (Map<String, Object> var:variables) {
            when(pizzaOrderingSystem.runsCallActivity("MultiInstance_Side_CallActivity"))
                    .thenReturn(Scenario.use(AssistantChef));
            when(pizzaOrderingSystem.waitsAtEventBasedGateway("EventBasedGateway_WaitForPayment"))
                    .thenReturn(gateway -> {
                        gateway.getEventSubscription().receive();
                    });
//            when(pizzaOrderingSystem.waitsAtServiceTask("JD_AddPrice"))
//                    .thenCallRealMethod();
            Scenario.run(pizzaOrderingSystem)
                    .startByMessage("orderMessage", var)
                    .execute();
            verify(pizzaOrderingSystem).hasCompleted("JD_pizza-validation");
            verify(pizzaOrderingSystem).hasCompleted("JD_side-validation");
        }
    }

    public static List<Map<String, Object>>  foodOrder(String path) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        //System.out.println("##Path:"+Paths.get("online_delivery_data.json").toAbsolutePath());
        List<Order> orders = mapper.readValue(Paths.get(path).toFile(), new TypeReference<List<Order>>() {
        });
        System.out.println("####"+orders.size());
       return orders.stream().map(PizzaPaymentWithMockitoTest::mapToOrderMap).collect(Collectors.toList());

    }

    private static Map<String, Object> mapToOrderMap(Order order) {
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
        return orderMap;
    }

}