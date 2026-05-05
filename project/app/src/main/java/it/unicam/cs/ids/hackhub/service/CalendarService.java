package it.unicam.cs.ids.hackhub.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import it.unicam.cs.ids.hackhub.entity.requester.CallRequester;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Servizio per l'integrazione con Google Calendar.
 * Fornisce metodi per creare eventi su Google Calendar e verificare la disponibilità del calendario in un intervallo di tempo specifico.
 */
@Service
public class CalendarService {

    @Value("${google.calendar.credential.path}")
    private String credentialPath;

    @Value("${google.calendar.id}")
    private String calendarId;

    @Value("${google.calendar.timezone:Europe/Rome}")
    private String timeZone;

    private Calendar calendarClient;

    /**
     * Metodo di inizializzazione del servizio, eseguito dopo la creazione dell'istanza.
     * Configura il client di Google Calendar utilizzando le credenziali fornite nel file specificato da credentialPath e impostando l'ambito di accesso a CalendarScopes.CALENDAR.
     *
     * @throws Exception in caso di errori durante la lettura delle credenziali o la configurazione del client di Google Calendar
     */
    @PostConstruct
    public void init() throws Exception {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(credentialPath))
                .createScoped(List.of(CalendarScopes.CALENDAR));
        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("HackHub").build();
    }

    /**
     * Metodo per creare un evento su Google Calendar.
     * Prima verifica la disponibilità del calendario nell'intervallo di tempo specificato, se è libero crea l'evento con i dettagli forniti da CallRequester e restituisce l'ID dell'evento creato. Se il calendario è occupato o si verificano errori durante la chiamata all'API, restituisce null o lancia un'eccezione.
     *
     * @param callRequester oggetto con i dettagli dell'evento da creare
     * @return ID dell'evento creato se la creazione è avvenuta con successo, null se il calendario è occupato o si verificano errori durante la chiamata all'api
     */
    public String createEvent(CallRequester callRequester) {
        FreeBusyRequest request = new FreeBusyRequest()
                .setTimeMin(toDateTime(callRequester.start()))
                .setTimeMax(toDateTime(callRequester.end()))
                .setTimeZone(timeZone)
                .setItems(List.of(new FreeBusyRequestItem().setId(calendarId)));
        try {
            if (freeBusyCheck(request)) {
                Event event = new Event()
                        .setSummary(callRequester.title())
                        .setStart(new EventDateTime().setDateTime(toDateTime(callRequester.start())))
                        .setEnd(new EventDateTime().setDateTime(toDateTime(callRequester.end())))
                        .setConferenceData(new ConferenceData().setCreateRequest(new CreateConferenceRequest().setRequestId(UUID.randomUUID().toString())));
                try {
                    Event created = insert(event);
                    return created.getId();
                } catch (IOException e) {
                    throw new RuntimeException("Errore durante la creazione dell'evento: " + e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il controllo di disponibilità: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Metodo per verificare la disponibilità di spazio nel calendario tramite la funzione freebusy di Google Calendar API.
     *
     * @param request richiesta di controllo disponibilità in formato FreeBusyRequest
     * @return true se il calendario è libero nell'intervallo specificato, false se è occupato
     * @throws IOException in caso di errori durante la chiamata all'api di Google Calendar
     */
    private boolean freeBusyCheck(FreeBusyRequest request) throws IOException {
        FreeBusyResponse response = calendarClient.freebusy().query(request).execute(); // Chiamata alla funzione Google FreeBusy

        if (response.getCalendars() == null || response.getCalendars().get(calendarId) == null)
            throw new IllegalStateException("FreeBusy response invalida per calendario: " + calendarId);
        List<TimePeriod> busySlots = response.getCalendars().get(calendarId).getBusy();
        return busySlots == null || busySlots.isEmpty(); // Il calendario è occupato in questo intervallo
    }

    /**
     * Metodo per inserire un evento su Google Calendar tramite la funzione events.insert di Google Calendar API.
     *
     * @param event evento da inserire su Google Calendar
     * @return l'evento creato con i dettagli restituiti da Google Calendar API, inclusi l'id dell'evento e i dettagli della conferenza
     * @throws IOException in caso di errori durante la chiamata all'api di Google Calendar
     */
    private Event insert(Event event) throws IOException {
        return calendarClient.events().insert(calendarId, event).setConferenceDataVersion(1).execute();
    }

    /**
     * Help method per la conversione da LocalDateTime a DateTime (utilizzato da Google Calendar API)
     *
     * @param localDateTime data in formato Java
     * @return DateTime data in formato Google
     */
    private DateTime toDateTime(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(timeZone));
        return new DateTime(Date.from(zonedDateTime.toInstant()));
    }
}