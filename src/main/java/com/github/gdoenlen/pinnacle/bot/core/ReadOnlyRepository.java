// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Query;

public abstract class ReadOnlyRepository<T> {
    protected final Class<T> clazz;
    protected final Database database;

    protected ReadOnlyRepository(Class<T> clazz) {
        this.clazz = clazz;
        this.database = DB.getDefault();
    }

    protected Query<T> query() {
        return this.database.createQuery(this.clazz);
    }
}
