package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.BotMessage;
import by.testbot.models.Client;
import by.testbot.models.Dialogue;
import by.testbot.repositories.DialogueRepository;

@Service
public class DialogueService {
    
    @Autowired
    private DialogueRepository dialogueRepository;

    @Transactional
    public void save(Dialogue dialogue) {
        dialogueRepository.save(dialogue);
    }

    @Transactional
    public void saveAll(Iterable<Dialogue> dialogues) {
        dialogueRepository.saveAll(dialogues);
    }

    @Transactional
    public void delete(Dialogue dialogue) {
        dialogueRepository.delete(dialogue);
    }

    @Transactional
    public void deleteAll(Iterable<Dialogue> dialogues) {
        dialogueRepository.deleteAll(dialogues);
    }

    @Transactional
    public List<Dialogue> getAll() {
        return dialogueRepository.findAll();
    }

    @Transactional
    public List<Dialogue> getAllByClient(Client client) {
        return dialogueRepository.findByClient(client);
    }

    @Transactional
    public List<Dialogue> getAllByBotMessage(BotMessage botMessage) {
        return dialogueRepository.findAllByBotMessage(botMessage);
    }

    @Transactional
    public List<Dialogue> getAllNotEndedAndBotMessageNotNullDialogues() {
        return dialogueRepository.findAllByDialogueIsOverAndBotMessageNotNull(false);
    }

    public Dialogue getCurrentDialogueByClient(Client client) {
        return getAllByClient(client).stream().filter(m -> m.getDialogueIsOver() == false).findFirst().orElse(null);
    }
}
