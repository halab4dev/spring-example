package com.github.halab4dev;

import lombok.Getter;

@Getter
public class DataPayload {

    private final String id;
    private final String payload;

    public DataPayload(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }

}

