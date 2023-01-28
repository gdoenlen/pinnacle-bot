// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import java.util.Collection;

import jakarta.annotation.Nullable;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Query;

public abstract class Repository<T extends Model> {
    protected final Class<T> clazz;
    protected final Database database;

    protected Repository(Class<T> clazz) {
        this.clazz = clazz;
        this.database = DB.getDefault();
    }

    /**
     * Finds an entity by its id
     * @param id the id of the entity you want to find
     * @return the entity or null
     */
    @Nullable
    public T findById(Long id) {
        return this.query()
            .where()
            .idEq(id)
            .findOne();
    }

    protected Query<T> query() {
        return this.database.createQuery(this.clazz);
    }

    public void insert(Collection<T> entities) {
        this.database.insertAll(entities);
    }

    public void insert(T t) {
        this.database.insert(t);
    }

    public void update(Collection<T> entities) {
        this.database.updateAll(entities);
    }

    public void update(T t) {
        this.database.update(t);
    }

    public void delete(Collection<T> entities) {
        this.database.deleteAll(entities);
    }

    public void delete(T t) {
        this.database.delete(t);
    }

    public T getReference(Long id) {
        return this.database.reference(this.clazz, id);
    }
}
