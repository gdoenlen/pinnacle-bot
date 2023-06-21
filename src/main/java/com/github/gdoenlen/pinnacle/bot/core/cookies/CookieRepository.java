// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies;

import java.util.Collection;

import com.github.gdoenlen.pinnacle.bot.core.cookies.query.QCookie;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import jakarta.enterprise.context.ApplicationScoped;

import com.github.gdoenlen.pinnacle.bot.core.Repository;

@ApplicationScoped
class CookieRepository extends Repository<Cookie> {
    CookieRepository() {
        super(Cookie.class);
    }

    Collection<Cookie> findByUser(User user) {
        return new QCookie().to.eq(user).findList();
    }
}
