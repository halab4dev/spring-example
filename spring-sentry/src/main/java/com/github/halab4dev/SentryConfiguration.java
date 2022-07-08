package com.github.halab4dev;

import javax.annotation.PostConstruct;

import io.sentry.Sentry;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Sentry configuration
 * @see "https://docs.sentry.io/clients/java/integrations/#spring"
 * @author halab
 */
@Configuration
public class SentryConfiguration {

    @PostConstruct
    public void init() {
        Sentry.init("<DSN_URL>?environment=staging");
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver();
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new io.sentry.spring.SentryServletContextInitializer();
    }
}
