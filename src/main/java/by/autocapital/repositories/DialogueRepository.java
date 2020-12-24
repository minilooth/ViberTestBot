package by.autocapital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.BotMessage;
import by.autocapital.models.Client;
import by.autocapital.models.Dialogue;

public interface DialogueRepository extends JpaRepository<Dialogue, Long> {
    public List<Dialogue> findByClient(Client client);
    public List<Dialogue> findAllByBotMessage(BotMessage botMessage);
    public List<Dialogue> findAllByDialogueIsOverAndBotMessageNotNull(Boolean dialogueIsOver);
}
