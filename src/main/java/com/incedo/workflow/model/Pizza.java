package com.incedo.workflow.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza implements Serializable {
	private String pizzaId;
	private String pizzaName;
	private List<String> toppings;
}