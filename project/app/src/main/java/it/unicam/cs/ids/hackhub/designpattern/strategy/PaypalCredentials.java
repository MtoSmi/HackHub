package it.unicam.cs.ids.hackhub.designpattern.strategy;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe che rappresenta le credenziali di PayPal, inclusi l'endpoint dell'API, il client ID e il client secret.
 */
@Getter
@Setter
public class PaypalCredentials {
    private String baseUrl;
    private String clientId;
    private String clientSecret;
}