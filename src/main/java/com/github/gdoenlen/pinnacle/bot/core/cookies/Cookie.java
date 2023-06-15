// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.core.cookies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.github.gdoenlen.pinnacle.bot.core.Model;
import com.github.gdoenlen.pinnacle.bot.core.users.User;

@Entity
public class Cookie extends Model {

    @NotNull
    @IsAllowedToGiveCookies
    @Column(nullable = false)
    @ManyToOne
    private User from;

    @NotNull
    @Column(nullable = false)
    @ManyToOne
    private User to;

    @Size(max = 255)
    private String reason;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
