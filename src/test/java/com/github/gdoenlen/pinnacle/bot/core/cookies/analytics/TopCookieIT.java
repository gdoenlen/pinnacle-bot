package com.github.gdoenlen.pinnacle.bot.core.cookies.analytics;

import com.github.gdoenlen.pinnacle.bot.core.users.User;
import com.github.gdoenlen.pinnacle.bot.utils.SqlGenerator;
import io.ebean.DB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

public class TopCookieIT {

//    @BeforeAll
    static void populateSystem() {
        String sql = """
            INSERT INTO users (id, username, created_at, last_modified_at, created_by_id, last_modified_by_id)
            VALUES (-1, 'SYSTEM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, -1, -1);
            """;
        DB.sqlUpdate(sql).execute();
    }

//    @Test
    void cookieLeaderboardWorks() {
        String userSql = SqlGenerator.generateUserInserts(12);
        DB.sqlUpdate(userSql).execute();
        List<User> users = DB.find(User.class).findList();
        String cookieSql = SqlGenerator.generateCookieInserts(75, users);
        DB.sqlUpdate(cookieSql).execute();
        TopCookieRepository topCookieRepository = new TopCookieRepository();
        Collection<TopCookie> topCookies = topCookieRepository.findTopTen();
        Assertions.assertEquals(10, topCookies.size());
    }
}
