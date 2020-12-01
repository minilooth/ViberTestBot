package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.ClientChatMessageHistory;

public interface ClientChatMessageHistoryRepository extends JpaRepository<ClientChatMessageHistory, Long> {
    public List<ClientChatMessageHistory> getByViberId(String viberId);
}
