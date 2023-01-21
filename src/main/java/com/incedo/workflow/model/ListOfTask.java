package com.incedo.workflow.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListOfTask {
     
	private boolean checkSelect;
	private String TaskTitle;
	private Date TaskCreatedTime;
	private Date DueDate;
	private String OwnerName;
	private int priority;
    private List<CamundaVariables> camundaVariable;
	private String TaskDef;
	private String camundaUser;
	private String GroupName;
	private String delegatestate;
	private String TaskId; 
	private String AssignedType;
}
