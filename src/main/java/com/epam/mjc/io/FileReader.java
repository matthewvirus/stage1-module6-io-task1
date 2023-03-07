package com.epam.mjc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileReader {

    public Profile getDataFromFile(File file) {
        HashMap<String, String> profileMap = extractMapFromFile(file);
        return new Profile(
                profileMap.get("Name"),
                Integer.parseInt(profileMap.get("Age")),
                profileMap.get("Email"),
                Long.parseLong(profileMap.get("Phone"))
        );
    }

    private HashMap<String, String> extractMapFromFile(File file) {
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
                    System.out.println("No Key:Value found in line, ignoring: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static void main(String[] args) {
        Profile profile = new FileReader().getDataFromFile(new File("src/main/resources/Profile.txt"));
        System.out.println(profile.toString());
    }
}