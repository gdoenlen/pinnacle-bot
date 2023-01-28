// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

/**
 * The application context.
 * The current implementation uses a thread local variable to hold the context
 * and so that other layers may access it easily. In the future, this should
 * probably be migrated to the ScopedValue implementation from JEP 429.
 *
 * @see <a href="https://openjdk.org/jeps/429">JEP 429<a>
 */
public record Context(User user) {
    private static final ThreadLocal<Context> CURRENT = new ThreadLocal<>();

    public static Context get() {
        Context current = CURRENT.get();
        if (current == null) {
            throw new IllegalStateException("The application context was not set.");
        }

        return current;
    }

    public static void set(Context context) {
        CURRENT.set(context);
    }

    public static void remove() {
        CURRENT.remove();
    }
}
