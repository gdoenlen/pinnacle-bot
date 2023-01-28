// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies;

import jakarta.enterprise.context.ApplicationScoped;

import com.github.gdoenlen.pinnacle.bot.core.Repository;

@ApplicationScoped
class CookieRepository extends Repository<Cookie> {
    CookieRepository() {
        super(Cookie.class);
    }
}
