package com.github.gdoenlen.pinnacle.bot.slack;

import com.github.gdoenlen.pinnacle.bot.core.Context;
import com.github.gdoenlen.pinnacle.bot.core.users.User;
import com.github.gdoenlen.pinnacle.bot.core.users.UserFacade;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContextualBotCommandTests {
    private static final String USERNAME = "test";

    private SlashCommandContext context;
    private UserFacade userFacade;
    private BotCommand delegate;

    @BeforeEach
    void beforeEach() {
        this.context = mock(SlashCommandContext.class);
        this.userFacade = mock(UserFacade.class);
        this.delegate = mock(BotCommand.class);

        when(context.getRequestUserId()).thenReturn(USERNAME);
        when(userFacade.findByUsername(anyString())).thenReturn(new User(USERNAME));
    }

    @Test
    void it_should_delegate_the_name() {
        when(this.delegate.name()).thenReturn("Test");
        var command = new ContextualBotCommand(this.delegate, this.userFacade);

        assertEquals(delegate.name(), command.name());
    }

    @Test
    void it_should_set_the_context_before_delegating() throws Exception {
        try (MockedStatic<Context> context = mockStatic(Context.class)) {
            var command = new ContextualBotCommand(this.delegate, this.userFacade);
            command.apply(null, this.context);

            context.verify(() -> Context.set(any()));
        }
    }

    @Test
    void it_should_always_remove_the_context_at_the_end() throws Exception {
        try (MockedStatic<Context> context = mockStatic(Context.class)) {
            var command = new ContextualBotCommand(this.delegate, this.userFacade);
            command.apply(null, this.context);

            context.verify(Context::remove);
        }
    }

    @Test
    void it_should_create_the_current_user_if_they_do_not_exist() {
        var facade = mock(UserFacade.class);
        when(facade.findByUsername(anyString())).thenReturn(null);

        var command = new ContextualBotCommand(this.delegate, facade);
        command.apply(null, this.context);

        verify(facade).insert(argThat(user -> user.getUsername().equals(USERNAME)));
    }
}
