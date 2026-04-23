package it.unicam.cs.ids.hackhub.paypal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaypalCredentials {
    private String baseUrl;
    private String clientId;
    private String clientSecret;
}