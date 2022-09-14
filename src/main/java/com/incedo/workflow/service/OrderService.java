package com.incedo.workflow.service;

import com.incedo.workflow.model.Item;
import com.incedo.workflow.model.Order;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    public Map<String, Object> getOrder(Order order) {


        List<Item> ItemList = new ArrayList<>();
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("pizzaList", order.getPizzaList());
        orderMap.put("sideList", order.getSideList());
        orderMap.put("drinksList", order.getDrinksList());
        orderMap.put("ItemList", ItemList);
         return orderMap;
    }

    public String getKey() {
        Random random = new Random();
        String bKey = String.valueOf(Math.abs(random.nextInt()));
         return bKey;
    }

}
