package com.incedo.workflow.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Side implements Serializable {
	private String sideId;
	private String sideName;
}
