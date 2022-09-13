package com.incedo.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {
    private String ItemId;
    private String ItemName;
    private int Price;
    private List<String> Comments;
}
