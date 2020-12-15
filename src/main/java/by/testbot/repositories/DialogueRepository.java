package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.BotMessage;
import by.testbot.models.Client;
import by.testbot.models.Dialogue;

public interface DialogueRepository extends JpaRepository<Dialogue, Long> {
    public List<Dialogue> findByClient(Client client);
    public List<Dialogue> findAllByBotMessage(BotMessage botMessage);
    public List<Dialogue> findAllByDialogIsOverAndBotMessageNotNull(Boolean dialogIsOver);
}
