package it.unicam.cs.ids.hackhub.designpattern.strategy;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PayByPayPal implements PayStrategy {

    private final PayPalHttpClient httpClient;

    @Autowired
    public PayByPayPal(PayPalConfiguration payPalConfiguration) {
        this.httpClient = payPalConfiguration.getHttpClient();
    }

    public Order pay(Double amount, String email) {
        OrderRequest orderRequest = buildOrderRequest(amount, email);
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
        try {
            return httpClient.execute(request).result();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile creare ordine di pagamento PayStrategy: ", e);
        }
    }

    private OrderRequest buildOrderRequest(Double amount, String email) {
        return new OrderRequest()
                .checkoutPaymentIntent("CAPTURE")
                .applicationContext(new ApplicationContext()
                        .returnUrl("http://localhost:8080/api/payments/success")
                        .cancelUrl("http://localhost:8080/api/payments/cancel"))
                .purchaseUnits(List.of(new PurchaseUnitRequest()
                        .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode("EUR")
                                .value(amount.toString()))
                        .payee(new Payee().email(email))
                ));

    }
}