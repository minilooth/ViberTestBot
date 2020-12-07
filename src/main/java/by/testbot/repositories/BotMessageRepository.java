package by.testbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.BotMessage;

public interface BotMessageRepository extends JpaRepository<BotMessage, Integer> {
    
}
