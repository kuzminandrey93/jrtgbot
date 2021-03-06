package com.github.kuzminandrey93.javarushtelegrambot.bot;

import com.github.kuzminandrey93.javarushtelegrambot.command.CommandContainer;
import com.github.kuzminandrey93.javarushtelegrambot.javarushclient.JavaRushGroupClient;
import com.github.kuzminandrey93.javarushtelegrambot.service.GroupSubService;
import com.github.kuzminandrey93.javarushtelegrambot.service.SendBotMessageServiceImpl;
import com.github.kuzminandrey93.javarushtelegrambot.service.StatisticsService;
import com.github.kuzminandrey93.javarushtelegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.kuzminandrey93.javarushtelegrambot.command.CommandName.NO;

/**
 * Telegram bot for Javarush Community from Javarush community.
 */

@Component
public class JavaRushTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    public JavaRushTelegramBot(TelegramUserService telegramUserService, JavaRushGroupClient groupClient, GroupSubService groupSubService,
                               @Value("#{'${bot.admins}'.split(',')}") List<String> admins, StatisticsService statisticsService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
                                                        telegramUserService, groupClient, groupSubService, admins, statisticsService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String userName = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, userName).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), userName).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
