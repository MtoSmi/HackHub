package it.unicam.cs.ids.hackhub.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CalendarService {

    @Value("${google.calendar.credential.path}")
    private String credentialPath;

    @Value("${google.calendar.id}")
    private String calendarId;

    @Value("${google.calendar.timezone:Europe/Rome}")
    private String timeZone;

    private Calendar calendarClient;

    @PostConstruct
    public void init() throws Exception {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(credentialPath))
                .createScoped(List.of(CalendarScopes.CALENDAR_READONLY));

        calendarClient = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("HackHub").build();
    }

    public List<Event> listEvents() throws IOException {
        Events events = calendarClient.events()
                .list(calendarId)
                .setSingleEvents(true)
                .setOrderBy("startTime")
                .execute();

        return events.getItems();
    }

    public String createEvent(CallRequester callRequester) throws IOException {
        if (freeBusyCheck(callRequester)) {
            Event event = new Event()
                    .setSummary(callRequester.getTitle())
                    .setStart(new DateTime(callRequester.getStart()))
                    .setEnd(new DateTime(callRequester.getEnd()));

            Event created = calendarClient.events().insert(calendarId, event).execute();
            return created.getId();
        }
        throw new IllegalStateException("Il calendario è occupato in questo intervallo");
        return null; // Indica che il calendario è occupato in questo intervallo
    }

    private boolean freeBusyCheck(CallRequester callRequester) throws IOException {
        DateTime start = new DateTime(callRequester.getStart());
        DateTime end = new DateTime(callRequester.getEnd());

        FreeBusyRequest request = new FreeBusyRequest()
                .setTimeMin(start)
                .setTimeMax(end)
                .setTimeZone(timeZone)
                .setItems(List.of(new FreeBusyRequestItem().setId(calendarId)));

        FreeBusyResponse response = calendarClient.freebusy().query(request).execute();

        if (response.getCalendars() == null || response.getCalendars().get(calendarId) == null) {
            throw new IllegalStateException("FreeBusy response invalida per calendario: " + calendarId);
        }

        List<TimePeriod> busySlots = response.getCalendars().get(calendarId).getBusy();

        return busySlots == null || busySlots.isEmpty(); // Il calendario è occupato in questo intervallo
    }
}