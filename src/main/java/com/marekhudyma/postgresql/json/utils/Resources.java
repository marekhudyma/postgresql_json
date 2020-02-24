package com.marekhudyma.postgresql.json.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Resources {

    public static String readFromResources(String fileName) {
        try {
            return new String(Files.readAllBytes(
                    Paths.get(Resources.class.getClassLoader().getResource(fileName).toURI()).toAbsolutePath()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}