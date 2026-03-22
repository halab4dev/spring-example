package com.github.halab4dev.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.halab4dev.repository.entity.StringList;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class StringJsonArrayWritingConverter implements Converter<StringList, String> {

    private final ObjectMapper objectMapper;

    public StringJsonArrayWritingConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String convert(StringList source) {
        if (source == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(source.values());
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Cannot write JSON array from Java value: " + source, exception);
        }
    }
}
