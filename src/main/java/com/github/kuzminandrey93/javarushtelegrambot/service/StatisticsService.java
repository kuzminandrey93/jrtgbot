package com.github.kuzminandrey93.javarushtelegrambot.service;

import com.github.kuzminandrey93.javarushtelegrambot.dto.StatisticDTO;

/**
 * Service for getting bot statistics.
 */
public interface StatisticsService {
    StatisticDTO countBotStatistic();
}
