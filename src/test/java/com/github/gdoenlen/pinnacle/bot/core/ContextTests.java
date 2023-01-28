package com.github.gdoenlen.pinnacle.bot.core;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContextTests {
    @Test
    void it_should_throw_if_the_context_was_not_set_and_asked_for() {
        Context.remove();
        assertThrows(IllegalStateException.class, Context::get);
    }

    @Test
    void it_should_set_and_get_the_context() {
        var context = new Context(new User("George"));
        Context.set(context);
        assertEquals(context, Context.get());
    }
}
