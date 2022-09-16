package com.incedo.workflow.util;


import com.incedo.workflow.controller.HomeController;
import com.incedo.workflow.service.OrderService;
import org.assertj.core.api.Assertions;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {


    private MockMvc mockMvc;

    @Mock
    private OrderService oderService;

    @Mock
    private RuntimeService runtimeService;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private HomeController homeController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .build();
    }


    @Test
    public void testInvokeProcess() throws Exception {
        String orderJson = getJsonFromFile("order.json");
        MvcResult result = mockMvc.perform(post("/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertThat(HttpStatus.OK.value() == response.getStatus());
        Assertions.assertThat(response.getContentAsString().equals("Pizza Processing BPM is Running."));
         System.out.println("Response+======================"+response.getContentAsString());
    }

    private String getJsonFromFile(String fileName) {
        String jsonText = "";
           try {
               ClassLoader classLoader = getClass().getClassLoader();
               File file = new File(classLoader.getResource(fileName).getFile());
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bufferedReader.readLine())  != null) {
                    jsonText += line + "\n";
                }
                bufferedReader.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        return jsonText;
    }

}