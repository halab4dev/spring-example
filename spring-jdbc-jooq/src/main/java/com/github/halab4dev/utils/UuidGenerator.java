package com.github.halab4dev.utils;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class UuidGenerator {

    public static String newV7Uuid() {
        UUID uuid = Generators.timeBasedEpochGenerator().generate(); // Version 7
        return uuid.toString();
    }
}
