package com.github.kuzminandrey93.javarushtelegrambot.javarushclient;

import com.github.kuzminandrey93.javarushtelegrambot.javarushclient.dto.PostInfo;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link JavaRushPostClient} interface.
 */
@Component
public class JavaRushPostClientImpl implements JavaRushPostClient{

    private final String javarushApiPostPath;

    public JavaRushPostClientImpl(@Value("${javarush.api.path}") String javarushApiPostPath) {
        this.javarushApiPostPath = javarushApiPostPath + "/posts";
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
        List<PostInfo> lastPostsByGroup = Unirest.get(javarushApiPostPath)
                .queryString("order", "NEW")
                .queryString("groupKid", groupId)
                .queryString("limit", 15)
                .asObject(new GenericType<List<PostInfo>>() {
                }).getBody();

        List<PostInfo> newPosts = new ArrayList<>();

        for(PostInfo post : lastPostsByGroup){
            if(lastPostId.equals(post.getId())){
                return newPosts;
            }
            newPosts.add(post);
        }
        return newPosts;
    }
}
