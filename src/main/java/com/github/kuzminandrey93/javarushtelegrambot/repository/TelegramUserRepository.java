package com.github.kuzminandrey93.javarushtelegrambot.repository;

import com.github.kuzminandrey93.javarushtelegrambot.repository.entity.TelegramUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@link Repository} for handling with {@link TelegramUser} entity.
 */

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, String>{
    List<TelegramUser> findAllByActiveTrue();
}
