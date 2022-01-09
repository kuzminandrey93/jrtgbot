package com.github.kuzminandrey93.javarushtelegrambot;

import com.github.kuzminandrey93.javarushtelegrambot.bot.JavaRushTelegramBot;
import com.github.kuzminandrey93.javarushtelegrambot.service.SendBotMessageService;
import com.github.kuzminandrey93.javarushtelegrambot.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private JavaRushTelegramBot javaRushTelegramBot;


    @BeforeEach
    public void init() {
        javaRushTelegramBot = Mockito.mock(JavaRushTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(javaRushTelegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(javaRushTelegramBot).execute(sendMessage);
    }

}
