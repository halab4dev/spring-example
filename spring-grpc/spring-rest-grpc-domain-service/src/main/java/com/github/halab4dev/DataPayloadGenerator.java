package com.github.halab4dev;

public class DataPayloadGenerator {

    public static String generatePayload(int sizeBytes) {
        if (sizeBytes <= 0) {
            return "";
        }
        char[] chars = new char[sizeBytes];
        for (int i = 0; i < sizeBytes; i++) {
            chars[i] = 'x';
        }
        return new String(chars);
    }
}
