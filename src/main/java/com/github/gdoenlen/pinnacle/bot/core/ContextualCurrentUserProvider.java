// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import jakarta.enterprise.context.ApplicationScoped;

import io.ebean.config.CurrentUserProvider;

@ApplicationScoped
class ContextualCurrentUserProvider implements CurrentUserProvider {
    @Override
    public Object currentUser() {
        return Context.get().user().getId();
    }
}
