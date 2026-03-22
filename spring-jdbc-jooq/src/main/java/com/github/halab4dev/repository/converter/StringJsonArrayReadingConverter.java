package com.github.halab4dev.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.halab4dev.repository.entity.StringList;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Collections;
import java.util.List;

@ReadingConverter
public class StringJsonArrayReadingConverter implements Converter<String, StringList> {

    private static final TypeReference<List<String>> LIST_OF_STRING = new TypeReference<>() {};
    private final ObjectMapper objectMapper;

    public StringJsonArrayReadingConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public StringList convert(String source) {
        if (source == null || source.isBlank()) {
            return new StringList(Collections.emptyList());
        }

        try {
            List<String> values = objectMapper.readValue(source, LIST_OF_STRING);
            return new StringList(values);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Cannot read JSON array from database value: " + source, exception);
        }
    }
}
