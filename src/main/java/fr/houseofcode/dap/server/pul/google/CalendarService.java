package fr.houseofcode.dap.server.pul.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.houseofcode.dap.server.pul.Utils;

/**
 * @author adminHOC
 */
@Service
public final class CalendarService {
    /**
     *the internal application name.
     */
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    /**
     *the default JsonFactory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * .
     * allow the secure access to GMail Account
     * @return an instance CalendarService with secured transport
     * @throws GeneralSecurityException if fail
     * @throws IOException if security failure
     */

    public String getNextEvent() throws IOException, GeneralSecurityException {
        String result = "";
        // Build a new authorized API client service.
        final NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(hTTPTRANSPORT, JSON_FACTORY, Utils.getCredentials(hTTPTRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            result = "aucun event.";
        } else {
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }

                result = result + event.getSummary() + " (" + start + ")\n";
                //System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
        return result;
    }
}
