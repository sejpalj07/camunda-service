package com.incedo.workflow.util;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestCase {
    private static final String PROCESS_KEY = "testCaseSample";

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"UnitTestCase.bpmn"})
    public void testSampleCase_happyPath() {

        ProcessInstance instance = runtimeService().startProcessInstanceByKey(PROCESS_KEY);

        assertThat(instance)
                .isActive()
                .hasPassed("startEvent")
                .isWaitingAtExactly("userTask1")
                .task().isNotAssigned();

        complete(task(), withVariables(
                "assignPerson", "sumanth",
                "attribute1", "value1"
        ));

        assertThat(instance)
                .hasPassed("userTask1")
                .hasPassedInOrder("userTask1", "serviceTask1")
                .isWaitingAt("userTask2")
                .task().isAssignedTo("sumanth");

        complete(task(), withVariables("attributeService", "variableServicevalue"));
        assertThat(instance)
                .hasPassedInOrder("userTask2", "endEvent")
                .isEnded();

    }
}
