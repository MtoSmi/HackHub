package it.unicam.cs.ids.hackhub.designpattern.strategy;

import com.paypal.orders.Order;

public interface PayStrategy {
    Order pay(Double amount, String email);
}