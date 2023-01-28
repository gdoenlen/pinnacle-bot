package com.github.gdoenlen.pinnacle.bot.core.cookies;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IsAllowedToGiveCookiesTests {
    @Test
    void it_should_fail_when_the_user_is_not_allowed_to_give_cookies() {
        var user = mock(User.class);
        when(user.isAllowedToGiveCookies()).thenReturn(false);

        var validator = new IsAllowedToGiveCookies.Validator();
        assertFalse(validator.isValid(user, null));
    }
}

