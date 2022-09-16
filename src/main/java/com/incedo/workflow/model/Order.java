package com.incedo.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    String deliveryType;
    Date pickupTime;
    String address;
    String validationMessage;
    String paymentType;
    CustomerInfo customerInfo;
    @NotNull(message = "PizzaList Can't be Null.")
    private List<Pizza> pizzaList;
    @NotNull(message = "sideList Can't be Null.")
    private List<Side> sideList;
    @NotNull(message = "drinksList Can't be Null.")
    private List<String> drinksList;
}
