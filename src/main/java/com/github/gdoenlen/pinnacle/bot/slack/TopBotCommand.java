// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import com.github.gdoenlen.pinnacle.bot.core.cookies.analytics.TopCookie;
import com.slack.api.methods.SlackApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.github.gdoenlen.pinnacle.bot.core.cookies.analytics.TopCookieRepository;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;

import java.io.IOException;
import java.util.List;

/** Command to display the top 10 cookie holders */
@ApplicationScoped
class TopBotCommand implements BotCommand {
    private final TopCookieRepository topCookieRepository;

    @Inject
    TopBotCommand(TopCookieRepository topCookieRepository) {
        this.topCookieRepository = topCookieRepository;
    }

    @Override
    public String name() {
        return "/top";
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext context) {
        List<TopCookie> topCookieList = this.topCookieRepository.findTopTen();
        StringBuilder leaderboard = new StringBuilder();
        leaderboard.append("Leaderboard:\n");
        for (int i = 0; i < topCookieList.size(); i++) {
            TopCookie topCookie = topCookieList.get(i);
            leaderboard.append(i + 1).append(". ").append(topCookie.username())
                .append(" (").append(topCookie.numCookies()).append(" cookies)\n");
        }
        try {
            context.client().chatPostMessage(r -> r
                .channel(context.getChannelId())
                .text(leaderboard.toString()));
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }

        return context.ack();
    }
}
