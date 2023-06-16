// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import com.github.gdoenlen.pinnacle.bot.core.Context;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import com.github.gdoenlen.pinnacle.bot.core.users.UserFacade;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import jdk.incubator.concurrent.ScopedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A decorator for bot commands that wraps the delegate command
 * and sets our application context before running.
 */
class ContextualBotCommand implements BotCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextualBotCommand.class);

    private final BotCommand delegate;
    private final UserFacade userFacade;

    ContextualBotCommand(BotCommand delegate, UserFacade userFacade) {
        this.delegate = delegate;
        this.userFacade = userFacade;
    }

    @Override
    public String name() {
        return this.delegate.name();
    }

    @Override
    public Response apply(SlashCommandRequest request, SlashCommandContext context) {
        var currentUser = this.findCurrentUser(context);

        try {
            return Context.call(new Context(currentUser), () -> this.delegate.apply(request, context));
        } catch (Exception ex) {
            LOGGER.error("Uncaught command exception.", ex);

            // The only sensible thing to do here is to acknowledge
            // the request or Slack will get angry.
            return context.ack();
        }
    }

    private User findCurrentUser(SlashCommandContext context) {
        String username = context.getRequestUserId();
        User user = this.userFacade.findByUsername(username);
        if (user == null) {
            user = new User(username);
            this.userFacade.insert(user);
        }

        return user;
    }
}
