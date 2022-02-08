package com.github.kuzminandrey93.javarushtelegrambot.service;

import com.github.kuzminandrey93.javarushtelegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.kuzminandrey93.javarushtelegrambot.repository.entity.GroupSub;

/**
 * Service for manipulating with {@link GroupSub}.
 */

public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
