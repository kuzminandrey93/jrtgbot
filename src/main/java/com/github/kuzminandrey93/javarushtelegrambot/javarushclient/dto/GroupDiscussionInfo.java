package com.github.kuzminandrey93.javarushtelegrambot.javarushclient.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Group discussion info class.
 */

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GroupDiscussionInfo extends GroupInfo{
    private UserDiscussionInfo userDiscussionInfo;
    private Integer commentsCount;
}
