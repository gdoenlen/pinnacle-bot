package com.github.gdoenlen.pinnacle.bot.core;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContextualCurrentUserProviderTests {
    @Test
    void it_should_return_the_id_of_the_user_from_the_context() {
        var user = new User("George");
        user.setId(0L);
        Context.set(new Context(user));

        var provider = new ContextualCurrentUserProvider();

        assertEquals(user, provider.currentUser());
        Context.remove();
    }
}
