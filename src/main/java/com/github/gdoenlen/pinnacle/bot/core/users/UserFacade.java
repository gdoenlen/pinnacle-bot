// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.users;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import com.github.gdoenlen.pinnacle.bot.core.Facade;
import com.github.gdoenlen.pinnacle.bot.core.Repository;

@ApplicationScoped
public class UserFacade extends Facade<User> {
    private final Validator validator;
    private final UserRepository repository;

    @Inject
    public UserFacade(Validator validator, UserRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public void insert(User user) {
        this.validate(user);
        this.repository.insert(user);
    }

    public void update(User user) {
        this.validate(user);
        this.repository.update(user);
    }

    @Override
    protected Repository<User> repository() {
        return this.repository;
    }

    public User findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    private void validate(User user) {
        Set<ConstraintViolation<User>> violations = this.validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
