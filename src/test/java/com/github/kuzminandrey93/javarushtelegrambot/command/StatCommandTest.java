package com.github.kuzminandrey93.javarushtelegrambot.command;

import static com.github.kuzminandrey93.javarushtelegrambot.command.CommandName.STAT;
import static com.github.kuzminandrey93.javarushtelegrambot.command.StatCommand.STAT_MESSAGE;

public class StatCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STAT_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
