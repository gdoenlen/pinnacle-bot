// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies.analytics;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

public class TopCookie {
    private final User user;
    private final long numCookies;

    public TopCookie(User user, long numCookies) {
        this.user = user;
        this.numCookies = numCookies;
    }

    public String username() {
        return this.user.getUsername();
    }

    public long numCookies() {
        return this.numCookies;
    }
}
