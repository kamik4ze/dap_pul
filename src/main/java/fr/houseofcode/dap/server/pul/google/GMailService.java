package fr.houseofcode.dap.server.pul.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import fr.houseofcode.dap.server.pul.Utils;

/**
 *
 * @author adminHOC
 *
 */
@Service
public final class GMailService {
    private static final Logger LOG = LogManager.getLogger();

    /**
    *the internal application name.
    */
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    /**
     *the default JsonFactory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * .
     * allow the secure access to GMail Account
     * @return an instance GmailService with secured transport
     * @throws GeneralSecurityException if fail
     * @throws IOException if security failure
     */

    private Gmail getService() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(hTTPTRANSPORT, JSON_FACTORY, Utils.getCredentials(hTTPTRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();
        return service;

        /**
         * @return A GmailInstance Service
         * @throws GeneralSecurityExeption .
         * @throws IOException if security failure.
         */
    }

    public String getLabels() throws IOException, GeneralSecurityException {
        LOG.debug("recupération des labels");
        String result = "";
        Gmail service2 = getService();
        // Print the labels in the user's account.
        String user = "me";
        ListLabelsResponse listResponse = service2.users().labels().list(user).execute();
        List<Label> labels = listResponse.getLabels();
        if (labels.isEmpty()) {
            System.out.println("No labels found.");
        }

        for (Label label : labels) {
            result = result + label.getName() + " \n";
            LOG.debug("nb de labels" + labels.size());
        }
        return result;
    }

    public Integer getNbUnreadEmail() throws IOException, GeneralSecurityException {
        final NetHttpTransport hTTPTRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(hTTPTRANSPORT, JSON_FACTORY, Utils.getCredentials(hTTPTRANSPORT))
                .setApplicationName(APPLICATION_NAME).build();
        String user = "me";
        ListMessagesResponse allMessages = service.users().messages().list(user).setQ("in:inbox is:unread").execute();
        List<Message> messages = allMessages.getMessages();
        if (messages.isEmpty()) {
            return 0;
        } else {

            int number = messages.size();

            return number;
        }
    }

}
