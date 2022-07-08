package com.github.halab4dev;

import io.sentry.Sentry;
import io.sentry.event.UserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author halab
 */
@Slf4j
@RestController
@RequestMapping("/sentry")
public class Controller {

    @GetMapping("/success")
    public String successRequest(
    ) {
        log.debug("successRequest");
        return "OK";
    }

    @GetMapping("/fail")
    public String failRequest(
            @RequestParam("message") String message
    ) {
        log.debug("failRequest");
        Sentry.getContext().setUser(new UserBuilder().setId("user id 1").build());
        Sentry.getContext().addTag("api", "fail");
        Sentry.getContext().addExtra("request data", message);
        throw new NullPointerException();
    }

    @GetMapping("/try-catch")
    public String tryCatchRequest(
            @RequestParam("message") String message
    ) {
        log.debug("tryCatchRequest");
        Sentry.getContext().setUser(new UserBuilder().setId("user id 1").build());
        Sentry.getContext().addTag("api", "fail");
        Sentry.getContext().addExtra("request data", message);
        try {
            throw new NullPointerException();
        } catch(Exception ex) {
            //Sentry can not catch this exception without logback-spring.xml config
            log.error("", ex);
            return "Not OK";
        }
    }
}
