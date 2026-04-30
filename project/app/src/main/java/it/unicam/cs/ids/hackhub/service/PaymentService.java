package it.unicam.cs.ids.hackhub.service;

import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import it.unicam.cs.ids.hackhub.designpattern.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PayStrategy payStrategy;

    @Autowired
    public PaymentService(PayStrategy payStrategy) {
        this.payStrategy = payStrategy;
    }

    public String initiatePayment(Double amount, String email) {
        Order order = payStrategy.pay(amount, email);
        if (order == null) throw new RuntimeException("Errore nel pagamento");
        return order.links().stream()
                .filter(link -> "approve".equals(link.rel()))
                .findFirst()
                .map(LinkDescription::href)
                .orElseThrow(() -> new RuntimeException("Link non trovato"));
    }
}