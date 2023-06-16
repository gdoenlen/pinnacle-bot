// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import jdk.incubator.concurrent.ScopedValue;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

/** The application context. This should always be set before any action is taken. */
public record Context(User user) {
    private static final ScopedValue<Context> CURRENT = ScopedValue.newInstance();

    public static <T> T call(Context context, Callable<T> fn) throws Exception {
        return ScopedValue.where(CURRENT, context)
            .call(fn);
    }

    public static Context current() {
        try {
            return CURRENT.get();
        } catch (NoSuchElementException ex) {
            throw new IllegalStateException(
                "The application context was not set. You should run your action with: Context::call.",
                ex
            );
        }
    }
}
