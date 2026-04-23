package it.unicam.cs.ids.hackhub.service;

import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import it.unicam.cs.ids.hackhub.paypal.PayPal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PayPal payPal;

    @Autowired
    public PaymentService(PayPal payPal) {
        this.payPal = payPal;
    }

    public String initiatePayment(Double amount, String email) {
        Order order = payPal.createOrder(amount, email);
        if (order == null) throw new RuntimeException("Errore nel pagamento");
        return order.links().stream()
                .filter(link -> "approve".equals(link.rel()))
                .findFirst()
                .map(LinkDescription::href)
                .orElseThrow(() -> new RuntimeException("Link non trovato"));
    }

    @Transactional
    public boolean confirmPayment(String orderId) {
        return payPal.captureOrder(orderId);
    }
}
