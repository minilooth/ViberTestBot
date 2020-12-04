package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete(Dialogue dialogue) {
        dialogueRepository.delete(dialogue);
    }

    @Transactional
    public List<Dialogue> getAll() {
        return dialogueRepository.findAll();
    }

    @Transactional
    public List<Dialogue> getAllByClient(Client client) {
        return dialogueRepository.findByClient(client);
    }

    public Dialogue getCurrentDialogByClient(Client client) {
        return getAllByClient(client).stream().filter(m -> m.getDialogIsOver() == false).findFirst().orElse(null);
    }
}
