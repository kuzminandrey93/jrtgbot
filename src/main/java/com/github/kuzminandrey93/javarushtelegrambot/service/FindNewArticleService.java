package com.github.kuzminandrey93.javarushtelegrambot.service;

/**
 * Service for finding new articles.
 */
public interface FindNewArticleService {
    /**
     * Find new posts and notify subscribers about it.
     */
    void findNewArticles();
}
