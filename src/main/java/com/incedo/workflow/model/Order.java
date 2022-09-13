package com.incedo.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    @NotNull(message = "PizzaList Can't be Null.")
    private List<Pizza> pizzaList;
    @NotNull(message = "sideList Can't be Null.")
    private  List<Side> sideList;
    @NotNull(message = "drinksList Can't be Null.")
    private List<String> drinksList;
    String deliveryType;
    String pickupTime;
    String address;
    String validationMessage;
    String paymentType;
    CustomerInfo customerInfo;

}
