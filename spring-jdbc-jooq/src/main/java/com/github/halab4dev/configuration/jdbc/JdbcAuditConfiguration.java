package com.github.halab4dev.configuration.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import java.util.Optional;

@Configuration
@EnableJdbcAuditing
public class JdbcAuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            // This is where you would get the current user from security context (e.g., Spring Security)
            // For example, return the username of the logged-in user
            return Optional.of("halab");
        };
    }
}
