package fr.houseofcode.dap.server.pul;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.pul.google.CalendarService;

/**
 * @author pul
 *
 */
@RestController
public class CalendarController {

    @Autowired
    private CalendarService calService;

    @RequestMapping("event/next")
    public String displayNextEvent() throws IOException, GeneralSecurityException {
        return calService.getNextEvent();
    }

}
