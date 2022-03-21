package com.github.kuzminandrey93.javarushtelegrambot.service;

import com.github.kuzminandrey93.javarushtelegrambot.javarushclient.JavaRushPostClient;
import com.github.kuzminandrey93.javarushtelegrambot.javarushclient.dto.PostInfo;
import com.github.kuzminandrey93.javarushtelegrambot.repository.entity.GroupSub;
import com.github.kuzminandrey93.javarushtelegrambot.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewArticleServiceImpl implements FindNewArticleService {

    private static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.ru/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public FindNewArticleServiceImpl(GroupSubService groupSubService, JavaRushPostClient javaRushPostClient,
                                     SendBotMessageService sendBotMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void findNewArticles() {
        groupSubService.findAll().forEach(groupSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(groupSub.getId(), groupSub.getLastArticleId());

            setNewLastArticleId(groupSub, newPosts);

            notifySubscribersAboutNewArticles(groupSub, newPosts);
        });
    }

    private void notifySubscribersAboutNewArticles(GroupSub groupSub, List<PostInfo> newPosts){
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨\n\n" +
                        "<b>Описание:</b> %s\n\n" +
                        "<b>Ссылка:</b> %s\n",
                        post.getTitle(), groupSub.getTitle(), post.getDescription(), getPostURL(post.getKey())))
                .collect(Collectors.toList());

        groupSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendBotMessageService.sendMessage(it.getChatId(), messagesWithNewArticles));
    }

    private void setNewLastArticleId(GroupSub groupSub, List<PostInfo> newPosts){
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    groupSub.setLastArticleId(id);
                    groupSubService.save(groupSub);
                });
    }

    private String getPostURL(String key){
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }
}
