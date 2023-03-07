package com.epam.mjc.io;

import org.apache.logging.log4j.core.impl.Log4jLogEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileReader {

    Logger log = LogManager.getLogManager().getLogger(Log4jLogEvent.class.getName());

    public Profile getDataFromFile(File file) {
        HashMap<String, String> profileMap = null;
        try {
            profileMap = extractMapFromFile(file);
        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
        }
        return new Profile(
                profileMap.get("Name"),
                Integer.parseInt(profileMap.get("Age")),
                profileMap.get("Email"),
                Long.parseLong(profileMap.get("Phone"))
        );
    }

    private HashMap<String, String> extractMapFromFile(File file) throws FileNotFoundException {
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
                    log.info("No Key: Value found in line, ignoring: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}