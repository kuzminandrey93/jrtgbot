package com.github.kuzminandrey93.javarushtelegrambot.command;

import com.github.kuzminandrey93.javarushtelegrambot.repository.entity.TelegramUser;
import com.github.kuzminandrey93.javarushtelegrambot.service.SendBotMessageService;
import com.github.kuzminandrey93.javarushtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.kuzminandrey93.javarushtelegrambot.command.CommandUtils.getChatId;

/**
 * Start {@link Command}.
 */

public class StartCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE = "Привет. Я Javarush Telegram Bot.\n " +
            "Я помогу тебе быть в курсе последних статей тех авторов, которые тебе интересны.\n\n" +
            "Нажимай /addGroupSub чтобы подписаться на группу статей в JavaRush.\n" +
            "Не знаешь о чем я? Напиши /help, чтобы узнать что я умею.";

    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatID = getChatId(update);

        telegramUserService.findByChatId(chatID).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                    },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatID);
                    telegramUserService.save(telegramUser);
                });

        sendBotMessageService.sendMessage(chatID, START_MESSAGE);
    }
}
