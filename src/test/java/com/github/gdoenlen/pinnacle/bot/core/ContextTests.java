package com.github.gdoenlen.pinnacle.bot.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ContextTests {
    @Test
    void it_should_throw_if_the_context_was_not_set_and_asked_for() {
        assertThrows(IllegalStateException.class, Context::current);
    }
}
