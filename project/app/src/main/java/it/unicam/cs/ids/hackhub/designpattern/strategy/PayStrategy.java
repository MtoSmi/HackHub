package it.unicam.cs.ids.hackhub.designpattern.strategy;

import com.paypal.orders.Order;

/**
 * Interfaccia del design pattern Strategy per l'implementazione di diverse tipologie di pagamento.
 */
public interface PayStrategy {
    /**
     * Esegue il pagamento utilizzando l'importo e l'email del destinatario specificati.
     *
     * @param amount importo del pagamento
     * @param email  email del primo membro del team (Creatore)
     * @return Order i dettagli dell'ordine creato
     */
    Order pay(Double amount, String email);
}