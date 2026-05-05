package it.unicam.cs.ids.hackhub;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configurazione CORS per l'applicazione HackHub.
 * QUESTA CLASSE VIENE UTILIZZATA SOLO PER INTERAGIRE CON IL FRONTEND IN LOCALE CHE NON FA PARTE DEL REPOSITORY.
 * CLASSE DA NON VALUTARE.
 * LA GRAFICA NON FA PARTE DEL REPOSITORY.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura le mappature CORS per consentire le richieste provenienti da origini specifiche.
     *
     * @param registry il registro CORS da configurare
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:63343", "http://localhost:3000", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}