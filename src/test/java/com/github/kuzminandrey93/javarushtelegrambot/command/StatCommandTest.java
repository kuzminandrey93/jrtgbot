package com.github.kuzminandrey93.javarushtelegrambot.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.kuzminandrey93.javarushtelegrambot.command.CommandName.STAT;
import static com.github.kuzminandrey93.javarushtelegrambot.command.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, telegramUserService.retrieveAllActiveUsers().size());
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
