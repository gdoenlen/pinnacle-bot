package com.github.gdoenlen.pinnacle.bot.core;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContextualCurrentUserProviderTests {
    @Test
    void it_should_return_the_user_from_the_context() throws Exception {
        var user = new User("George");
        user.setId(0L);
        Context.call(new Context(user), () -> {
            var provider = new ContextualCurrentUserProvider();

            assertEquals(user, provider.currentUser());
            return null;
        });
    }
}
