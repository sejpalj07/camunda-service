package com.incedo.workflow.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Side implements Serializable {
    private String sideId;
    private String sideName;
}
