package com.epam.mjc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReader {

    private static final Logger log = Logger.getLogger(FileReader.class.getName());

    public Profile getDataFromFile(File file) {
        HashMap<String, String> profileMap = new HashMap<>();
        try {
            profileMap = extractMapFromFile(file);
        } catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        }
        return new Profile(
                profileMap.get("Name"),
                Integer.parseInt(profileMap.get("Age")),
                profileMap.get("Email"),
                Long.parseLong(profileMap.get("Phone"))
        );
    }

    private HashMap<String, String> extractMapFromFile(File file) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] keyValuePair = line.split(": ");
                if (keyValuePair.length > 1) {
                    String key = keyValuePair[0];
                    String value = keyValuePair[1];
                    map.put(key, value);
                } else {
                    log.log(Level.INFO, "No Key: Value found in line, ignoring");
                }
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return map;
    }
}