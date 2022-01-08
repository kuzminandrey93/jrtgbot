package com.github.kuzminandrey93.javarushtelegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    /**
     * Main method, which is executing command logic.
     *
     * @param update provided {@link Update} object with all the needed data for command.
     */

    public void execute(Update update);
}
