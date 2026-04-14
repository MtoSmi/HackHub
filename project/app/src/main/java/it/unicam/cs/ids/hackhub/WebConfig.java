package it.unicam.cs.ids.hackhub;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configurazione Web MVC dell'applicazione.
 *
 * <p>Questa classe abilita e centralizza le regole CORS per gli endpoint REST
 * esposti sotto <code>/api/**</code>, permettendo a pagine HTML locali (in sviluppo)
 * di effettuare chiamate HTTP verso il backend senza blocchi del browser.</p>
 *
 * <p>Origini consentite attualmente:
 * <ul>
 *   <li><code><a href="http://localhost:63342">...</a></code> (es. Pagine locali aperte da IDE)</li>
 *   <li><code>http://localhost:3000</code> (tipico dev server frontend)</li>
 *   <li><code>http://localhost:5173</code> (tipico dev server Vite)</li>
 * </ul>
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registra le policy CORS applicate agli endpoint API.
     *
     * <p>La configurazione permette:
     * <ul>
     *   <li>Metodi HTTP principali: GET, POST, PUT, DELETE, OPTIONS</li>
     *   <li>Qualsiasi header in ingresso</li>
     *   <li>Invio di credenziali (cookie/header auth) quando necessario</li>
     *   <li>Cache del preflight per 3600 secondi</li>
     * </ul>
     * </p>
     *
     * @param registry registro Spring usato per definire i mapping CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:63342", "http://localhost:3000", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}