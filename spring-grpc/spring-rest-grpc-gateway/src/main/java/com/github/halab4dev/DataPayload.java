package com.github.halab4dev;

import lombok.Data;

@Data
public class DataPayload {

    private String id;
    private String payload;

    public DataPayload() {
    }

    public DataPayload(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }
}

