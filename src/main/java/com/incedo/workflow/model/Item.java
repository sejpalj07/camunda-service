package com.incedo.workflow.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {
	private String ItemId;
	private String ItemName;
	private int Price;
	private List<String> Comments;
}