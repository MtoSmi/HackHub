package it.unicam.cs.ids.hackhub.designpattern.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Classe di configurazione per PayPal che gestisce le credenziali e l'endpoint dell'API.
 * Le credenziali possono essere fornite tramite variabili d'ambiente, file JSON o configurazione Spring.
 */
@Getter
@Component
public class PayPalConfiguration {

    @Value("${paypal.base-url:https://api-m.sandbox.paypal.com}")
    private String baseUrl;

    @Value("${paypal.client-id:}")
    private String clientId;

    @Value("${paypal.client-secret:}")
    private String clientSecret;

    @Value("${paypal.credentials.path:./config/paypal-credentials.json}")
    private String credentialsPath;

    private PayPalHttpClient httpClient;

    private String resolvedBaseUrl;
    private String resolvedClientId;
    private String resolvedClientSecret;

    /**
     * Inizializza la configurazione PayPal risolvendo le credenziali e l'endpoint.
     */
    @PostConstruct
    public void init() {
        if (StringUtils.hasText(clientId) && StringUtils.hasText(clientSecret)) {
            resolvedBaseUrl = StringUtils.hasText(baseUrl) ? baseUrl : "https://api-m.sandbox.paypal.com"; // Sandbox di default
            resolvedClientId = clientId.trim();
            resolvedClientSecret = clientSecret.trim();
        } else {
            loadFromJson();
        }
        PayPalEnvironment environment;
        if (resolvedBaseUrl != null && resolvedBaseUrl.contains("sandbox")) {
            environment = new PayPalEnvironment.Sandbox(resolvedClientId, resolvedClientSecret);
        } else {
            environment = new PayPalEnvironment.Live(resolvedClientId, resolvedClientSecret);
        }
        httpClient = new PayPalHttpClient(environment);
    }

    /**
     * Carica le credenziali PayPal da un file JSON specificato da credentialsPath.
     */
    private void loadFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PaypalCredentials creds = mapper.readValue(new File(credentialsPath), PaypalCredentials.class);
            if (!StringUtils.hasText(creds.getClientId()) || !StringUtils.hasText(creds.getClientSecret())) {
                throw new IllegalStateException("File JSON PayStrategy non valido: mancano clientId o clientSecret");
            }
            resolvedBaseUrl = StringUtils.hasText(creds.getBaseUrl()) ? creds.getBaseUrl().trim() : "https://api-m.sandbox.paypal.com";
            resolvedClientId = creds.getClientId().trim();
            resolvedClientSecret = creds.getClientSecret().trim();
        } catch (IOException e) {
            throw new IllegalStateException("Impossibile leggere le credenziali PayStrategy da " + credentialsPath, e);
        }
    }
}