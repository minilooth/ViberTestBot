package by.autocapital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.BotMessage;
import by.autocapital.models.Button;

public interface ButtonRepository extends JpaRepository<Button, Long> {
    public Button findFirstByCallbackData(String callbackData);
    public List<Button> findAllByBotMessage(BotMessage botMessage);
}
