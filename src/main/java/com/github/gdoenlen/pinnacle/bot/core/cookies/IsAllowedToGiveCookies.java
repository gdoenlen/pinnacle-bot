// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsAllowedToGiveCookies.Validator.class)
@SuppressWarnings("unused")
@interface IsAllowedToGiveCookies {
    String message() default "User is not allowed to give cookies.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IsAllowedToGiveCookies, User> {
        @Override
        public boolean isValid(User user, ConstraintValidatorContext context) {
            return user != null && user.isAllowedToGiveCookies();
        }
    }
}
