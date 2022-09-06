package com.incedo.workflow.util;

import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@RunWith(MockitoJUnitRunner.class)
public class CalDiscountDmnTestCase {
    private static final String PROCESS_KEY = "testCaseSample";

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Test
    public void setup(){
        init(rule.getProcessEngine());
    }

    @Test
    @Deployment(resources = {"cal-discount.dmn"})
    public void testSampleCase_happyPath_rule1() {

        DmnDecisionResult dmnDecisionResult = processEngine().getDecisionService()
                .evaluateDecisionByKey("cal-discount")
                .variables(withVariables("price",100,"category","ctg1"))
                .evaluate();

        assertEquals(1,dmnDecisionResult.size());
        assertEquals(dmnDecisionResult.get(0).get("discount"),10);
    }

    @Test
    @Deployment(resources = {"cal-discount.dmn"})
    public void testSampleCase_happyPath_rule2() {

        DmnDecisionResult dmnDecisionResult = processEngine().getDecisionService()
                .evaluateDecisionByKey("cal-discount")
                .variables(withVariables("price",120,"category","ctg1"))
                .evaluate();

        assertEquals(1,dmnDecisionResult.size());
        assertEquals(dmnDecisionResult.get(0).get("discount"),15);
    }

    @Test
    @Deployment(resources = {"cal-discount.dmn"})
    public void testSampleCase_happyPath_rule3() {

        DmnDecisionResult dmnDecisionResult = processEngine().getDecisionService()
                .evaluateDecisionByKey("cal-discount")
                .variables(withVariables("price",600,"category","ctg1"))
                .evaluate();

        assertEquals(1,dmnDecisionResult.size());
        assertEquals(dmnDecisionResult.get(0).get("discount"),20);
    }

    @Test
    @Deployment(resources = {"cal-discount.dmn"})
    public void testSampleCase_happyPath_rule4() {

        DmnDecisionResult dmnDecisionResult = processEngine().getDecisionService()
                .evaluateDecisionByKey("cal-discount")
                .variables(withVariables("price",120,"category","ctg2"))
                .evaluate();

        assertEquals(1,dmnDecisionResult.size());
        assertEquals(dmnDecisionResult.get(0).get("discount"),50);
    }
}
