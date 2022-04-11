package com.github.kuzminandrey93.javarushtelegrambot.command.annotation;

import com.github.kuzminandrey93.javarushtelegrambot.command.Command;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mark if {@link Command} can be viewed only by admins.
 */
@Retention(RUNTIME)
public @interface AdminCommand {
}
