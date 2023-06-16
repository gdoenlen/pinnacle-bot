// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import io.ebean.config.CurrentUserProvider;

public class ContextualCurrentUserProvider implements CurrentUserProvider {
    @Override
    public User currentUser() {
        return Context.current().user();
    }
}
