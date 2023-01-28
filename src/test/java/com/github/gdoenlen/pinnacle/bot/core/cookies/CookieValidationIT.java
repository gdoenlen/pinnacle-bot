package com.github.gdoenlen.pinnacle.bot.core.cookies;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CookieValidationIT {
    private static final String USERNAME = "test-user";

    private Validator validator;

    @BeforeEach
    void beforeEach() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    @Test
    void it_should_fail_when_from_user_is_null() {
        var cookie = new Cookie();
        cookie.setTo(new User(USERNAME));

        Set<ConstraintViolation<Cookie>> violations = this.validator.validate(cookie);
        assertEquals(violations.size(), 2);
    }

    @Test
    void it_should_fail_when_to_user_is_null() {
        var cookie = new Cookie();
        cookie.setFrom(new User(USERNAME));

        Set<ConstraintViolation<Cookie>> violations = this.validator.validate(cookie);
        assertEquals(violations.size(), 1);
    }

    @Test
    void it_should_fail_when_from_user_is_not_allowed_to_give_cookies() {
        var fromUser = mock(User.class);
        when(fromUser.isAllowedToGiveCookies()).thenReturn(false);

        var cookie = new Cookie();
        cookie.setFrom(fromUser);
        cookie.setTo(new User(USERNAME));

        Set<ConstraintViolation<Cookie>> violations = this.validator.validate(cookie);
        assertEquals(violations.size(), 1);
    }
}
