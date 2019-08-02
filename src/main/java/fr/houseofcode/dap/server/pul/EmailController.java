package fr.houseofcode.dap.server.pul;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.pul.google.GMailService;

/**
 * @author pul
 *
 */
@RestController
public class EmailController {

    @Autowired
    private GMailService gmailService;

    @RequestMapping("/email/nbUnread")
    public Integer displayNbUnreadEmail() throws IOException, GeneralSecurityException {
        return gmailService.getNbUnreadEmail();
    }

    @RequestMapping("/email/labels")
    public String displayLabels() throws IOException, GeneralSecurityException {
        return gmailService.getLabels();
    }

}
