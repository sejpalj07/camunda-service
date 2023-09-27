package com.incedo.workflow.controller;

import com.incedo.workflow.model.CamundaVariables;
import com.incedo.workflow.model.ListOfTask;
import com.incedo.workflow.model.Order;
import com.incedo.workflow.model.ProcessDefinitionModel;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class TaskEngineController {
	private final RuntimeService runtimeService;
	private final ProcessEngine processEngineService;
	private final TaskService taskService;
	private final Random random = new Random();

	public TaskEngineController(@Autowired RuntimeService runtimeService, @Autowired TaskService taskService,
			@Autowired ProcessEngine processEngineService) {
		this.runtimeService = runtimeService;
		this.taskService = taskService;
		this.processEngineService = processEngineService;
	}

	@CrossOrigin
	@GetMapping("/custom/alltasks")
	public ResponseEntity<List<ListOfTask>> getAllTasks() {
		List<Task> tasks = taskService.createTaskQuery().list();

		List<ListOfTask> listOfTask = tasks.stream().map(value -> {
			ListOfTask lot = new ListOfTask();
			// get list of Variables
			List<CamundaVariables> listOfVariables = taskService.getVariables(value.getId()).keySet().stream()
					.map(value1 -> {
						CamundaVariables camundaVar = new CamundaVariables();
						camundaVar.setCamundaVariableName(value1);
						if (taskService.getVariable(value.getId(), value1) != null)
							camundaVar.setCamundaVariableValue(
									taskService.getVariable(value.getId(), value1).toString().toString());
						return camundaVar;
					}).collect(Collectors.toList());
			List<IdentityLink> groupName = taskService.getIdentityLinksForTask(value.getId());
			groupName.forEach(val -> {
				lot.setGroupName(val.getGroupId());
				lot.setCamundaUser(val.getUserId());
				lot.setAssignedType(val.getType());
			});
			lot.setProcessDefId(value.getProcessDefinitionId());
			lot.setCamundaVariable(listOfVariables);
			lot.setTaskTitle(value.getName());
			lot.setTaskDef(value.getTaskDefinitionKey());
			lot.setPriority(value.getPriority());
			lot.setTaskCreatedTime(value.getCreateTime());
			lot.setDueDate(value.getDueDate());
			lot.setOwnerName(value.getOwner());
			lot.setCheckSelect(false);
			lot.setTaskId(value.getId());
			// lot.setCamundaUser(tsk.getCamundaUser());
			return lot;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(listOfTask);
	}

	@CrossOrigin
	@PostMapping(path = "/custom/usertask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ListOfTask>> getUserTasks(@Valid @RequestBody ListOfTask tsk) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(tsk.getCamundaUser()).list();

		List<ListOfTask> listOfTask = tasks.stream().map(value -> {
			ListOfTask lot = new ListOfTask();
			// get list of Variables
			List<CamundaVariables> listOfVariables = taskService.getVariables(value.getId()).keySet().stream()
					.map(value1 -> {
						CamundaVariables camundaVar = new CamundaVariables();
						camundaVar.setCamundaVariableName(value1);
						if (taskService.getVariable(value.getId(), value1) != null)
							camundaVar.setCamundaVariableValue(
									taskService.getVariable(value.getId(), value1).toString().toString());
						return camundaVar;
					}).collect(Collectors.toList());
			List<IdentityLink> groupName = taskService.getIdentityLinksForTask(value.getId());
			groupName.forEach(val -> {
				lot.setGroupName(val.getGroupId());
				lot.setCamundaUser(val.getUserId());
				lot.setAssignedType(val.getType());
			});
			lot.setProcessDefId(value.getProcessDefinitionId());
			lot.setCamundaVariable(listOfVariables);
			lot.setTaskTitle(value.getName());
			lot.setTaskDef(value.getTaskDefinitionKey());
			lot.setPriority(value.getPriority());
			lot.setTaskCreatedTime(value.getCreateTime());
			lot.setDueDate(value.getDueDate());
			lot.setOwnerName(value.getOwner());
			lot.setCheckSelect(false);
			lot.setTaskId(value.getId());
			// lot.setCamundaUser(tsk.getCamundaUser());
			return lot;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(listOfTask);
	}

	@CrossOrigin
	@PostMapping(path = "/custom/usergrouptask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ListOfTask>> getUserGroupTasks(@Valid @RequestBody ListOfTask tsk) {
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(tsk.getCamundaUser()).list();
		List<ListOfTask> listOfTask = tasks.stream().map(value -> {
			ListOfTask lot = new ListOfTask();
			// get list of Variables
			List<CamundaVariables> listOfVariables = taskService.getVariables(value.getId()).keySet().stream()
					.map(value1 -> {
						CamundaVariables camundaVar = new CamundaVariables();
						camundaVar.setCamundaVariableName(value1);
						if (taskService.getVariable(value.getId(), value1) != null)
							camundaVar.setCamundaVariableValue(
									taskService.getVariable(value.getId(), value1).toString().toString());
						return camundaVar;
					}).collect(Collectors.toList());
			List<IdentityLink> groupName = taskService.getIdentityLinksForTask(value.getId());
			groupName.forEach(val -> {
				lot.setGroupName(val.getGroupId());
			});
			lot.setProcessDefId(value.getProcessDefinitionId());
			lot.setCamundaUser(tsk.getCamundaUser());
			lot.setCamundaVariable(listOfVariables);
			lot.setTaskTitle(value.getName());
			lot.setTaskDef(value.getTaskDefinitionKey());
			lot.setPriority(value.getPriority());
			lot.setTaskCreatedTime(value.getCreateTime());
			lot.setDueDate(value.getDueDate());
			lot.setOwnerName(value.getOwner());
			lot.setCheckSelect(false);
			lot.setTaskId(value.getId());
			return lot;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(listOfTask);
	}

	@CrossOrigin
	@PostMapping(path = "/custom/claim", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String DelegateTask(@Valid @RequestBody ListOfTask tsk) {
		String responseMessage;
		try {
			taskService.claim(tsk.getTaskId(), tsk.getCamundaUser());
		} catch (Exception e) {
			responseMessage = "{\"postStatus\"" + ":\"" + e.getMessage() + "\"}";
			return responseMessage;
		}
		if (tsk.getCamundaUser() == null)
			responseMessage = "{\"postStatus\"" + ":\"" + "Task Unclaimed Successfully" + "\"}";
		else
			responseMessage = "{\"postStatus\"" + ":\"" + "Task Claimed by " + tsk.getCamundaUser() + " Successfully"
					+ "\"}";
		return responseMessage;
	}

	@CrossOrigin
	@PostMapping(path = "/custom/delegate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String DelegateTask(@RequestParam("IsGroup") String IsGroup, @RequestParam("DelegateTo") String DelegateTo,
			@Valid @RequestBody List<ListOfTask> tsk) {
		String responseMessage = "{\"postStatus\"" + ":\"" + "Task Assigned to " + DelegateTo + " Successfully" + "\"}";
		try {
			tsk.forEach(tskValue -> {
				taskService.claim(tskValue.getTaskId(), null);
				if (IsGroup.equals("user")) {

					taskService.delegateTask(tskValue.getTaskId(), DelegateTo);
				} else {
					taskService.addCandidateGroup(tskValue.getTaskId(), DelegateTo);
				}
			});
		} catch (Exception e) {
			responseMessage = e.getMessage();
		}
		return responseMessage;
	}

	@CrossOrigin
	@PostMapping(path = "/custom/completetask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCompleteTask(@RequestParam("approvalStatus") String approvalStatus,
			@Valid @RequestBody List<ListOfTask> tsk) {
		String responseMessage = "";
		String responseMsg = "";
		try {
			tsk.forEach(tskValue -> {
				List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey(tskValue.getTaskDef())
						.taskCreatedOn(tskValue.getTaskCreatedTime()).list();
				tasks.forEach(value -> {
					if (approvalStatus.equals("approved"))
						taskService.setVariable(value.getId(), "AccountManagementApproval", "approved");
					else
						taskService.setVariable(value.getId(), "AccountManagementApproval", "rejected");
					taskService.complete(value.getId());
				});
			});
		} catch (Exception e) {
			responseMsg = e.getMessage();
		}
		if (responseMsg == "")
			responseMsg = "Selected tasks " + approvalStatus + " successfully";
		responseMessage = "{\"postStatus\"" + ":\"" + responseMsg + "\"}";
		return responseMessage;
	}

}
