// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import com.github.gdoenlen.pinnacle.bot.core.Facade;
import com.github.gdoenlen.pinnacle.bot.core.Repository;

@ApplicationScoped
public class CookieFacade extends Facade<Cookie> {

    private final Validator validator;
    private final CookieRepository repository;

    @Inject
    public CookieFacade(Validator validator, CookieRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public void insert(Cookie cookie) {
        Set<ConstraintViolation<Cookie>> violations = this.validator.validate(cookie);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        this.repository.insert(cookie);
    }

    @Override
    protected Repository<Cookie> repository() {
        return this.repository;
    }
}
