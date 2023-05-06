// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import static java.util.Objects.requireNonNull;

public abstract class Facade<T extends Model> {

    protected abstract Repository<T> repository();

    public T getReference(Long id) {
        return this.repository().getReference(requireNonNull(id));
    }
}
