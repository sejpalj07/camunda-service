package com.incedo.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo implements Serializable {
    private String name;
    private String phonenumber;
    private String alternatePhoneNumber;
}
