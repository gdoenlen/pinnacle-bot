// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies.analytics;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import com.github.gdoenlen.pinnacle.bot.core.ReadOnlyRepository;
import com.github.gdoenlen.pinnacle.bot.core.cookies.Cookie;

@ApplicationScoped
public class TopCookieRepository extends ReadOnlyRepository<Cookie> {
    TopCookieRepository() {
        super(Cookie.class);
    }

    public Collection<TopCookie> findTopTen() {
        // todo this should probably be raw sql
        return this.database.find(Cookie.class)
            .findList()
            .stream()
            .collect(Collectors.groupingBy(Cookie::getTo, Collectors.counting()))
            .entrySet()
            .stream()
            .map(entry -> new TopCookie(entry.getKey(), entry.getValue()))
            .sorted(Comparator.comparingLong(TopCookie::numCookies).reversed())
            .limit(10L)
            .toList();
    }
}
