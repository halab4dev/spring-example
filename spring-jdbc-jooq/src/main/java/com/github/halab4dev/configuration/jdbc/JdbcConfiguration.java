package com.github.halab4dev.configuration.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.halab4dev.repository.converter.StringJsonArrayReadingConverter;
import com.github.halab4dev.repository.converter.StringJsonArrayWritingConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.List;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected List<?> userConverters() {
        return List.of(
                new StringJsonArrayReadingConverter(objectMapper),
                new StringJsonArrayWritingConverter(objectMapper)
        );
    }
}
