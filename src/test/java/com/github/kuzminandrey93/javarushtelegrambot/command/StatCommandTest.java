package com.github.kuzminandrey93.javarushtelegrambot.command;

import com.github.kuzminandrey93.javarushtelegrambot.dto.GroupStatDTO;
import com.github.kuzminandrey93.javarushtelegrambot.dto.StatisticDTO;
import com.github.kuzminandrey93.javarushtelegrambot.service.SendBotMessageService;
import com.github.kuzminandrey93.javarushtelegrambot.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.kuzminandrey93.javarushtelegrambot.command.AbstractCommandTest.prepareUpdate;
import static com.github.kuzminandrey93.javarushtelegrambot.command.CommandName.STAT;
import static com.github.kuzminandrey93.javarushtelegrambot.command.StatCommand.STAT_MESSAGE;
import static java.lang.String.format;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest{

    private SendBotMessageService sendBotMessageService;
    private StatisticsService statisticsService;
    private StatCommand statCommand;

    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticsService = Mockito.mock(StatisticsService.class);
        statCommand = new StatCommand(sendBotMessageService, statisticsService);
    }

    @Test
    public void shouldProperlySendMessage(){
        //given
        Long chatId = 1234567L;
        GroupStatDTO groupStatDTO = new GroupStatDTO(1, "group", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(groupStatDTO),2.5);
        Mockito.when(statisticsService.countBotStatistic()).thenReturn(statisticDTO);

        //when
        statCommand.execute(prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId.toString(), format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                format("%s (id = %s) - %s ??????????????????????", groupStatDTO.getTitle(), groupStatDTO.getId(), groupStatDTO.getActiveUserCount())));
    }
}
