package com.github.gdoenlen.pinnacle.bot.utils;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.github.gdoenlen.pinnacle.bot.utils.DataGenerator.getRandomFullName;

public class SqlGenerator {

    private static final Random RANDOM = new Random();
    private static final String[] COMMENTS = {
        "Great job",
        "Well done",
        "Fantastic work",
        "Excellent effort",
        "Youre doing great",
        "Keep up the good work",
        "Nice work, keep it up",
        "You did a great job on this",
        "Youve done a fantastic job",
        "Your hard work really paid off",
        "Good job, Im impressed",
        "Youre doing a fantastic job",
        "Superb work, keep it going",
        "Your work is outstanding",
        "Youre doing a wonderful job",
        "Youve done exceptionally well",
        "Keep up the excellent work",
        "You should be proud of your effort",
        "Youve done a remarkable job",
        "Im impressed with your work"
    };

    public static String generateUserInserts(int numUsers) {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < numUsers; i++) {
            if (i != 0) {
                sql.append("\n");
            }
            String username = getRandomFullName();
            String insert = "INSERT INTO users (username, created_at, last_modified_at, created_by_id, last_modified_by_id) " +
                "VALUES ('" + username + "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, -1, -1);";
            sql.append(insert);
        }
        return sql.toString();
    }

    public static String generateCookieInserts(int numCookies, List<User> users) {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < numCookies; i++) {
            if (i != 0) {
                sql.append("\n");
            }
            User fromUser = getRandomUserThatIsNotThisUser(users, null);
            User toUser = getRandomUserThatIsNotThisUser(users, fromUser);
            String insert = String.format(
                "INSERT INTO cookie (from_id, to_id, reason, created_at, last_modified_at, created_by_id, last_modified_by_id) " +
                "VALUES (%d, %d, '%s', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, -1, -1); ",
                fromUser.getId(), toUser.getId(), getRandomMessage());
            sql.append(insert);
        }
        return sql.toString();
    }

    private static User getRandomUserThatIsNotThisUser(List<User> users, User thisUser) {
        User returnVal = null;
        while (returnVal == null || Objects.equals(returnVal, thisUser) || returnVal.getId() == -1) {
            returnVal = users.get(RANDOM.nextInt(users.size()));
        }
        return returnVal;
    }

    private static String getRandomMessage() {
        return COMMENTS[RANDOM.nextInt(COMMENTS.length)];
    }
}
