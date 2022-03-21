package com.github.kuzminandrey93.javarushtelegrambot.service;

import com.github.kuzminandrey93.javarushtelegrambot.bot.JavaRushTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Implementation of {@link SendBotMessageService} interface.
 */

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final JavaRushTelegramBot javaRushTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaRushTelegramBot javaRushTelegramBot) {
        this.javaRushTelegramBot = javaRushTelegramBot;
    }


    @Override
    public void sendMessage(String chatId, String message) {
        if (isBlank(message)) return;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            javaRushTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }

    }

    @Override
    public void sendMessage(String chatId, List<String> messages) {
        if(CollectionUtils.isEmpty(messages)){
            return;
        }
        messages.forEach(m -> sendMessage(chatId, m));
    }
}
