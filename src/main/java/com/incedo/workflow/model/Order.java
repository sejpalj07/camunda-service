package com.incedo.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private List<Pizza> pizzaList;
    private  List<Side> sideList;
    private List<String> drinksList;
    String deliveryType;
    String pickupTime;
    String address;
    String validationMessage;
    String paymentType;
    CustomerInfo customerInfo;

}
