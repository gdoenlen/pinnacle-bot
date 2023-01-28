// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.users;

import jakarta.enterprise.context.ApplicationScoped;

import com.github.gdoenlen.pinnacle.bot.core.Repository;
import com.github.gdoenlen.pinnacle.bot.core.users.query.QUser;

@ApplicationScoped
class UserRepository extends Repository<User> {
    UserRepository() {
        super(User.class);
    }

    User findByUsername(String username) {
        return new QUser().username.eq(username).findOne();
    }
}
