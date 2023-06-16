// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;

import com.github.gdoenlen.pinnacle.bot.core.Context;
import com.github.gdoenlen.pinnacle.bot.core.cookies.Cookie;
import com.github.gdoenlen.pinnacle.bot.core.cookies.CookieFacade;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import com.github.gdoenlen.pinnacle.bot.core.users.UserFacade;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;

import static com.github.gdoenlen.pinnacle.bot.slack.ResponseType.IN_CHANNEL;

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
        User currentUser = Context.current().user();
        var cookie = new Cookie();
        cookie.setFrom(currentUser);
        cookie.setTo(null); // todo find from the request?
        cookie.setReason("todo");

        try {
            this.cookieFacade.insert(cookie);
        } catch (ConstraintViolationException ex) {
            // todo
        }

        return context.ack(res -> res.responseType(IN_CHANNEL).text("todo!"));
    }
}
