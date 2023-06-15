// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.github.gdoenlen.pinnacle.bot.core.users.User;

import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;

@MappedSuperclass
public abstract class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @WhenCreated
    protected Instant createdAt;

    @WhenModified
    protected Instant lastModifiedAt;

    @WhoCreated
    @ManyToOne
    protected User createdBy;

    @WhoModified
    @ManyToOne
    protected User lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getLastModifiedAt() {
        return this.lastModifiedAt;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public User getLastModifiedBy() {
        return this.lastModifiedBy;
    }
}
