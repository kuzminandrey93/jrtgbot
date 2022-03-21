package com.github.kuzminandrey93.javarushtelegrambot.service;

import com.github.kuzminandrey93.javarushtelegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.kuzminandrey93.javarushtelegrambot.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */

public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);

    Optional<GroupSub> findById(Integer id);

    GroupSub save(GroupSub groupSub);

    List<GroupSub> findAll();
}
