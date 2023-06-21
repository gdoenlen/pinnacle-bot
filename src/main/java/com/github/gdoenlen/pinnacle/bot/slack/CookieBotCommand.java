// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import com.github.gdoenlen.pinnacle.bot.core.Context;
import com.github.gdoenlen.pinnacle.bot.core.cookies.Cookie;
import com.github.gdoenlen.pinnacle.bot.core.cookies.CookieFacade;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import com.github.gdoenlen.pinnacle.bot.core.users.UserFacade;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.gdoenlen.pinnacle.bot.slack.ResponseType.IN_CHANNEL;

@ApplicationScoped
class CookieBotCommand implements BotCommand {
    private final Pattern TARGET_USER_REGEX = Pattern.compile("<@(?<username>\\w*)|.*>");
    private final Pattern REASON_REGEX = Pattern.compile(">\\s*(?<reason>.*)");
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

        User targetUser = findTargetUser(request, context);
        if (targetUser == null) {
            //TODO respond with user not found or something
            return context.ack();
        }
        String reasonText = findReasonText(request);

        cookie.setTo(targetUser);
        cookie.setReason(reasonText);

        try {
            this.cookieFacade.insert(cookie);
        } catch (ConstraintViolationException ex) {
            String text = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
            return context.ack(res -> res.responseType(IN_CHANNEL).text(text));
        }

        return context.ack(res ->
            res.responseType(IN_CHANNEL).text("\uD83C\uDF6A✔")
        );
    }

    private String findReasonText(SlashCommandRequest request) {
        String requestText = request.getPayload().getText();
        if (StringUtils.isBlank(requestText)) {
            return null;
        }

        Matcher reasonTextMatcher = REASON_REGEX.matcher(requestText);
        if (!reasonTextMatcher.find()) {
            return StringUtils.EMPTY;
        }

        return reasonTextMatcher.group("reason");
    }

    private User findTargetUser(SlashCommandRequest request, SlashCommandContext context) {
        String requestText = request.getPayload().getText();
        if (StringUtils.isBlank(requestText)) {
            return null;
        }

        Matcher userMatcher = TARGET_USER_REGEX.matcher(requestText);
        if (!userMatcher.find()) {
            return null;
        }

        String userNameText = userMatcher.group("username");
        User existingUser = this.userFacade.findByUsername(userNameText);
        if (existingUser != null) {
            return existingUser;
        }

        // Create a new user
        User newUser = new User(userNameText);
        Context.run(new Context(newUser), () -> this.userFacade.insert(newUser));

        return newUser;
    }
}
