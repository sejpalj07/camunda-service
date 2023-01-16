package com.incedo.workflow.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.incedo.workflow.model.CamundaVariables;

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

}
