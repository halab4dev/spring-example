package com.github.halab4dev.repository.entity;

import java.util.Collections;
import java.util.List;

public record StringList(List<String> values) {

    public StringList {
        values = values == null ? Collections.emptyList() : List.copyOf(values);
    }
}
