// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot.slack;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;

import com.github.gdoenlen.pinnacle.bot.core.users.UserFacade;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jakarta_jetty.SlackAppServer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.weld.environment.se.events.ContainerBeforeShutdown;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@ApplicationScoped
class SlackAppServerProducer {
    @Produces
    @ApplicationScoped
    SlackAppServer produceSlackAppServer(
        @Any Instance<BotCommand> commands,
        @ConfigProperty(name = "slack.signing.secret") String secret,
        @ConfigProperty(name = "slack.bot.token") String token,
        UserFacade userFacade
    ) {
        var config = AppConfig.builder()
            .signingSecret(secret)
            .singleTeamBotToken(token)
            .build();

        var app = new App(config);
        for (BotCommand command : commands) {
            command = new ContextualBotCommand(command, userFacade);
            app.command(command.name(), command);
        }

        return new SlackAppServer(app);
    }

    void onStart(
        @SuppressWarnings({"unused", "java:S1172"})
        @Observes
        ContainerInitialized initialized,
        SlackAppServer server
    ) throws Exception {
        server.start();
    }

    void onStop(
        @SuppressWarnings({"unused", "java:S1172"})
        @Observes
        ContainerBeforeShutdown shutdown,
        SlackAppServer server
    ) throws Exception {
        server.stop();
    }
}
