package it.unicam.cs.ids.hackhub.strategy;

import com.paypal.orders.Order;

public interface PayPal {
    Order createOrder(Double amount, String email);

    boolean captureOrder(String orderId);
}
