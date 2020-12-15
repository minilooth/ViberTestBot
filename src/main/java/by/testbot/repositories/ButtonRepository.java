package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.BotMessage;
import by.testbot.models.Button;

public interface ButtonRepository extends JpaRepository<Button, Long> {
    public Button findFirstByCallbackData(String callbackData);
    public List<Button> findAllByBotMessage(BotMessage botMessage);
}
