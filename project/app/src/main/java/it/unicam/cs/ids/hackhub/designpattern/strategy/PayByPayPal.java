package it.unicam.cs.ids.hackhub.designpattern.strategy;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementazione della strategia di pagamento PayPal.
 * Utilizza l'SDK PayPal per creare un ordine di pagamento e restituire i dettagli dell'ordine.
 */
@Component
public class PayByPayPal implements PayStrategy {

    /**
     * Client HTTP per interagire con l'api PayPal.
     */
    private final PayPalHttpClient httpClient;

    /**
     * Costruttore che accetta una configurazione PayPal per inizializzare il client HTTP.
     */
    @Autowired
    public PayByPayPal(PayPalConfiguration payPalConfiguration) {
        this.httpClient = payPalConfiguration.getHttpClient();
    }

    /**
     * Crea un ordine di pagamento PayPal con l'importo e l'email del destinatario specificati.
     *
     * @param amount importo del pagamento
     * @param email  email del destinatario del pagamento
     * @return i dettagli dell'ordine creato
     */
    public Order pay(Double amount, String email) {
        OrderRequest orderRequest = buildOrderRequest(amount, email);
        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
        try {
            return httpClient.execute(request).result();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile creare ordine di pagamento PayStrategy: ", e);
        }
    }

    /**
     * Costruisce una richiesta di ordine PayPal con l'importo e l'email del destinatario specificati.
     */
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