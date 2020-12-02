package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.ClientMessage;

public interface ClientMessageRepository extends JpaRepository<ClientMessage, Long> {
    public List<ClientMessage> getByViberId(String viberId);
}
