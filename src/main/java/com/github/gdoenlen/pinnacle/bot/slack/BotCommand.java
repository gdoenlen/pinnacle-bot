// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import com.slack.api.bolt.handler.builtin.SlashCommandHandler;

public interface BotCommand extends SlashCommandHandler {
    String name();
}
