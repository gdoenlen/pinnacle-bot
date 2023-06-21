// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import org.apache.commons.lang3.StringUtils;

class RequestUtils {
    private static final Pattern TARGET_USER_REGEX = Pattern.compile("<@(?<username>\\w*)|.*>");

    private RequestUtils() {}

    static String findMentionedUser(SlashCommandRequest request) {
        String requestText = request.getPayload().getText();
        if (StringUtils.isBlank(requestText)) {
            return null;
        }

        Matcher userMatcher = TARGET_USER_REGEX.matcher(requestText);
        if (!userMatcher.find()) {
            return null;
        }

        return userMatcher.group("username");
    }
}
