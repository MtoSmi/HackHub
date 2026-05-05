package it.unicam.cs.ids.hackhub.service;

import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import it.unicam.cs.ids.hackhub.designpattern.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servizio per la gestione dei pagamenti tramite PayPal.
 * Utilizza il design pattern Strategy per supportare diverse strategie di pagamento.
 */
@Service
public class PaymentService {
    /**
     * Strategia di pagamento utilizzata.
     */
    private final PayStrategy payStrategy;

    /**
     * Costruttore del servizio di pagamento, che accetta una strategia di pagamento come parametro.
     *
     * @param payStrategy la strategia di pagamento da utilizzare per i pagamenti
     */
    @Autowired
    public PaymentService(PayStrategy payStrategy) {
        this.payStrategy = payStrategy;
    }

    /**
     * Inizia un processo di pagamento per un importo specificato e un'email associata al pagamento.
     *
     * @param amount l'importo del pagamento da effettuare
     * @param email  l'email del proprietario del team a cui è associato il pagamento
     * @return l'identificatore del pagamento, che può essere utilizzato per completare il processo di pagamento
     */
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