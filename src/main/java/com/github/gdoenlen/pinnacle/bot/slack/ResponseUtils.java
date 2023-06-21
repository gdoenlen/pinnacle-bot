package com.github.gdoenlen.pinnacle.bot.slack;

import java.io.IOException;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;

import static com.github.gdoenlen.pinnacle.bot.slack.ResponseType.IN_CHANNEL;

class ResponseUtils {
    private ResponseUtils() {}

    static Response inChannel(String message, SlashCommandContext context) {
        return context.ack(res -> res.responseType(IN_CHANNEL).text(message));
    }

    static String getDisplayName(String username, SlashCommandContext context) throws SlackApiException, IOException {
        return context.client()
            .usersInfo(r -> r.user(username).token(context.getBotToken()))
            .getUser()
            .getProfile()
            .getDisplayName();
    }
}
