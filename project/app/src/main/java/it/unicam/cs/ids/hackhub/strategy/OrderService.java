package it.unicam.cs.ids.hackhub.strategy;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class OrderService implements PayPal {

    private final PayPalHttpClient httpClient;

    @Autowired
    public OrderService(PayPalConfiguration payPalConfiguration) {
        this.httpClient = payPalConfiguration.getHttpClient();
    }

    public Order createOrder(Double amount, String email) {
        OrderRequest orderRequest = buildOrderRequest(amount, email);
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
        try {
            return httpClient.execute(request).result();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile creare ordine di pagamento PayPal: ", e);
        }
    }

    public boolean captureOrder(String orderId) {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderActionRequest());
        try {
            Order result = httpClient.execute(request).result();
            return "COMPLETED".equals(result.status());
        } catch (IOException e) {
            return false;
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
