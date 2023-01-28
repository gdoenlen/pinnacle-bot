package com.github.gdoenlen.pinnacle.bot.core;

public abstract class Facade<T extends Model> {

    protected abstract Repository<T> repository();

    public T getReference(Long id) {
        return this.repository().getReference(id);
    }
}
