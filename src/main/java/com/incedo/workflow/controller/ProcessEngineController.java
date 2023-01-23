package com.incedo.workflow.controller;

import com.incedo.workflow.model.ProcessDefinitionModel;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.xml.impl.util.ModelIoException;
import org.camunda.bpm.model.xml.instance.DomDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class ProcessEngineController {
    private final ProcessEngine processEngineService;
    private final DecisionService decisionService;

    @CrossOrigin
    @GetMapping("/custom/processdefination")
    public ResponseEntity<List<ProcessDefinitionModel>> getAllProcessDefinitions() {
        List<ProcessDefinition> prodentity = processEngineService.getRepositoryService().createProcessDefinitionQuery().list();
        List<ProcessDefinitionModel> sampleName = prodentity.stream().map(value -> {
            ProcessDefinitionModel pdm = new ProcessDefinitionModel();
            pdm.setDefinitionId(value.getId());
            pdm.setName(value.getName());
            pdm.setDefinitionKey(value.getKey());
            pdm.setVersion(value.getVersion());
            pdm.setVersionTag(value.getVersionTag());
            return pdm;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(sampleName);
    }

    @CrossOrigin
    @GetMapping("/custom/decisiondefinition")
    public ResponseEntity<List<ProcessDefinitionModel>> getAllDecisionDefinitions() {
        List<DecisionDefinition> prodentity = processEngineService.getRepositoryService().createDecisionDefinitionQuery().list();
        List<ProcessDefinitionModel> sampleName = prodentity.stream().map(value -> {
            ProcessDefinitionModel pdm = new ProcessDefinitionModel();
            pdm.setDefinitionId(value.getId());
            pdm.setName(value.getName());
            pdm.setDefinitionKey(value.getKey());
            pdm.setVersion(value.getVersion());
            pdm.setVersionTag(value.getVersionTag());
            return pdm;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(sampleName);
    }

    @CrossOrigin
    @GetMapping(value = "/custom/processdefination/{definitionId}/xml")
    public ResponseEntity<BpmXMlResponse> getProcessXML(@PathVariable("definitionId") String definitionId) {
        DomDocument diagramLayout = processEngineService.getRepositoryService().getBpmnModelInstance(definitionId).getDocument();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        StreamResult resul = null;
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            synchronized (diagramLayout) {
                transformer.transform(diagramLayout.getDomSource(), new StreamResult(outputStream));
            }
        } catch (TransformerConfigurationException e) {
            throw new ModelIoException("Unable to create a transformer for the model", e);
        } catch (TransformerException e) {
            throw new ModelIoException("Unable to transform model to xml", e);
        }
        BpmXMlResponse bpmXMlResponse = new BpmXMlResponse(definitionId, outputStream.toString());
        return ResponseEntity.ok(bpmXMlResponse);
    }

}
