package com.incedo.workflow.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo implements Serializable {
	private String name;
	private String phonenumber;
	private String alternatePhoneNumber;
}