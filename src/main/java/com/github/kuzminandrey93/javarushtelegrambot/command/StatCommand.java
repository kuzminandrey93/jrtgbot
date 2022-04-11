package com.github.kuzminandrey93.javarushtelegrambot.command;

import com.github.kuzminandrey93.javarushtelegrambot.command.annotation.AdminCommand;
import com.github.kuzminandrey93.javarushtelegrambot.dto.StatisticDTO;
import com.github.kuzminandrey93.javarushtelegrambot.service.SendBotMessageService;
import com.github.kuzminandrey93.javarushtelegrambot.service.StatisticsService;
import com.github.kuzminandrey93.javarushtelegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.kuzminandrey93.javarushtelegrambot.command.CommandUtils.getChatId;

/**
 * Statistics {@link Command}.
 */
@AdminCommand
public class StatCommand implements Command{

    private final StatisticsService statisticsService;
    private final SendBotMessageService sendBotMessageService;

    public final static String STAT_MESSAGE = "✨<b>Подготовил статистику</b>✨\n" +
            "- Количество активных пользователей: %s\n" +
            "- Количество неактивных пользователей: %s\n" +
            "- Среднее количество групп на одного пользователя: %s\n\n" +
            "<b>Информация по активным группам</b>:\n" +
            "%s";

    @Autowired
    public StatCommand(SendBotMessageService sendBotMessageService, StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroups = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collectedGroups));
    }
}
