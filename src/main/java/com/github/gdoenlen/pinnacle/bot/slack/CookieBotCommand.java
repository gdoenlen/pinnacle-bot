// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.github.gdoenlen.pinnacle.bot.core.Context;
import com.github.gdoenlen.pinnacle.bot.core.cookies.Cookie;
import com.github.gdoenlen.pinnacle.bot.core.cookies.CookieFacade;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import com.github.gdoenlen.pinnacle.bot.core.users.UserFacade;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;

@ApplicationScoped
class CookieBotCommand implements BotCommand {

    private final CookieFacade cookieFacade;
    private final UserFacade userFacade;

    @Inject
    CookieBotCommand(CookieFacade cookieFacade, UserFacade userFacade) {
        this.cookieFacade = cookieFacade;
        this.userFacade = userFacade;
    }

    @Override
    public String name() {
        return "/cookie";
    }

    @Override
    public Response apply(SlashCommandRequest request, SlashCommandContext context) {
        User currentUser = Context.get().user();
        if (currentUser.isAllowedToGiveCookies()) {
            var cookie = new Cookie();
            cookie.setFrom(currentUser);
            cookie.setTo(null); // todo
            cookie.setReason("todo");

            this.cookieFacade.insert(cookie);

            return context.ack("TODO!");
        }

        return context.ack("TODO!");
    }
}
