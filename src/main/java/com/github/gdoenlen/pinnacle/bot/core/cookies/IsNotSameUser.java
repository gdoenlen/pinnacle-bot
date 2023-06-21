package com.github.gdoenlen.pinnacle.bot.core.cookies;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.gdoenlen.pinnacle.bot.core.users.User;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsNotSameUser.Validator.class)
@interface IsNotSameUser {
    String message() default "You cannot give yourself a cookie.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<IsAllowedToGiveCookies, Cookie> {
        @Override
        public boolean isValid(Cookie cookie, ConstraintValidatorContext context) {
            User from = cookie.getFrom();

            return from != null && !from.equals(cookie.getTo());
        }
    }
}
