// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.github.gdoenlen.pinnacle.bot.core.cookies.analytics.TopCookieRepository;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;

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
        return "top";
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext context) {
        this.topCookieRepository.findTopTen()
            .forEach(System.out::println);

        return context.ack("todo");
    }
}
