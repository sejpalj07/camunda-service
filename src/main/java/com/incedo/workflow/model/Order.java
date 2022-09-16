package com.incedo.workflow.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
	@NotNull(message = "PizzaList Can't be Null.")
	private List<Pizza> pizzaList;
	@NotNull(message = "sideList Can't be Null.")
	private List<Side> sideList;
	@NotNull(message = "drinksList Can't be Null.")
	private List<String> drinksList;
	String deliveryType;
	Date pickupTime;
	String address;
	String validationMessage;
	String paymentType;
	CustomerInfo customerInfo;

}