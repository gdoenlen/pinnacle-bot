// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.users;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;

import com.github.gdoenlen.pinnacle.bot.core.Model;

import io.ebean.annotation.Formula;

@Entity
@Table(name = "users")
public class User extends Model {

    @NotNull
    @Column(unique = true, nullable = false)
    private final String username;

    @Formula(
        select = "lgt.lastGivenCookieTimestamp",
        join = """
               left join (
                 select MAX(created_at) as lastGivenCookieTimestamp, from_id
                 from cookie group by from_id
               ) as lgt
               on lgt.from_id = ${ta}.id
               """
    )
    private Instant lastGivenCookieTimestamp;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setLastGivenCookieTimestamp(Instant lastGivenCookieTimestamp) {
        this.lastGivenCookieTimestamp = lastGivenCookieTimestamp;
    }

    public boolean isAllowedToGiveCookies() {
        return this.lastGivenCookieTimestamp == null
            || LocalDateTime.ofInstant(this.lastGivenCookieTimestamp, ZoneId.systemDefault())
                .plusDays(1L)
                .isBefore(LocalDateTime.now());
    }
}
