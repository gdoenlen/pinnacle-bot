// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

import com.github.gdoenlen.pinnacle.bot.core.cookies.Cookie;
import com.github.gdoenlen.pinnacle.bot.core.cookies.CookieFacade;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import com.github.gdoenlen.pinnacle.bot.core.users.UserFacade;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static com.github.gdoenlen.pinnacle.bot.slack.ResponseUtils.getDisplayName;

@ApplicationScoped
class ListCookiesBotCommand implements BotCommand {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        .withZone(ZoneId.systemDefault());

    private final UserFacade userFacade;
    private final CookieFacade cookieFacade;

    @Inject
    ListCookiesBotCommand(UserFacade userFacade, CookieFacade cookieFacade) {
        this.userFacade = userFacade;
        this.cookieFacade = cookieFacade;
    }

    @Override
    public String name() {
        return "/list-cookies";
    }

    @Override
    public Response apply(SlashCommandRequest request, SlashCommandContext context)
        throws SlackApiException, IOException
    {
        String username = RequestUtils.findMentionedUser(request);
        if (username == null) {
            return ResponseUtils.inChannel("Please mention the user you want to list cookies for.", context);
        }

        Collection<Cookie> cookies = Collections.emptyList();
        User user = this.userFacade.findByUsername(username);
        if (user != null) {
            cookies = this.cookieFacade.findByUser(user);
        }
        return ResponseUtils.inChannel(buildCookieList(cookies, username, context), context);
    }

    private static String buildCookieList(Collection<Cookie> cookies, String user, SlashCommandContext context)
        throws SlackApiException, IOException
    {
        StringBuilder builder = new StringBuilder("```")
            .append("@")
            .append(getDisplayName(user, context))
            .append("'s cookies:\n");
        int i = 1;
        for (Cookie cookie : cookies) {
            builder.append(i)
                .append(". ")
                .append("@")
                .append(getDisplayName(cookie.getFromUsername(), context))
                .append(": ")
                .append(cookie.getReason())
                .append(" (")
                .append(DATE_FORMATTER.format(cookie.getCreatedAt()))
                .append(")")
                .append("\n");

            i++;
        }

        return builder.append("```").toString();
    }
}
