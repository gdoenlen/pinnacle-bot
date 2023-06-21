package com.github.gdoenlen.pinnacle.bot.utils;

import java.util.Random;

public class DataGenerator {
    private static final String[] firstNames = {
        "John", "Jane", "Alex", "Alice", "Charlie", "Chloe",
        "Daniel", "Diana", "Ethan", "Emma", "Frank", "Fiona",
        "George", "Grace", "Harry", "Hannah", "Ian", "Ivy",
        "Jack", "Julia"
    };
    private static final String[] lastNames = {
        "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis",
        "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas",
        "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia",
        "Martinez", "Robinson"
    };
    private static final Random random = new Random();

    // Generates a random string of a given length
    private static String generateRandomString(int length) {
        int leftLimit = 97; // ASCII value for 'a'
        int rightLimit = 122; // ASCII value for 'z'
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomValue = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomValue);
        }
        return buffer.toString();
    }

    public static String getRandomFullName() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        return firstName + " " + lastName + generateRandomString(4);
    }
}
