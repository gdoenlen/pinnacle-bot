// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies.analytics;

import com.github.gdoenlen.pinnacle.bot.core.ReadOnlyRepository;
import com.github.gdoenlen.pinnacle.bot.core.cookies.Cookie;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import io.ebean.SqlRow;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TopCookieRepository extends ReadOnlyRepository<Cookie> {
    TopCookieRepository() {
        super(Cookie.class);
    }

    public List<TopCookie> findTopTen() {
        String sql = """
            SELECT
                u.id,
                u.username,
                COUNT(c.to_id) AS cookie_count
            FROM users u
            JOIN cookie c ON u.id = c.to_id
            GROUP BY u.username
            ORDER BY cookie_count DESC
            LIMIT 10;
            """;
        List<SqlRow> sqlRows = this.database.sqlQuery(sql).findList();
        List<TopCookie> cookieList = new ArrayList<>();
        for (SqlRow row : sqlRows) {
            User user = new User(row.getString("username"));
            user.setId(row.getLong("id"));
            cookieList.add(new TopCookie(user, row.getLong("cookie_count")));
        }
        return cookieList;
    }
}
