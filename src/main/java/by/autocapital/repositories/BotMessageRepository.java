package by.autocapital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.BotMessage;

public interface BotMessageRepository extends JpaRepository<BotMessage, Integer> {
    public BotMessage findFirstByPreviousMessageAndIsAdded(BotMessage previousMessage, Boolean isAdded);
    public BotMessage findFirstByNextMessageAndIsAdded(BotMessage nextMessage, Boolean isAdded);
    public List<BotMessage> findAllByIsAdded(Boolean isAdded);
}