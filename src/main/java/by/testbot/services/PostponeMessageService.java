package by.testbot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.testbot.models.PostponeMessage;
import by.testbot.repositories.PostponeMessageRepository;

@Service
public class PostponeMessageService {
    @Autowired
    private PostponeMessageRepository postponeMessageRepository;

    public void save(PostponeMessage postponeMessage) {
        postponeMessageRepository.save(postponeMessage);
    }

    public void update(PostponeMessage postponeMessage) {
        postponeMessageRepository.save(postponeMessage);
    }

    public void delete(PostponeMessage postponeMessage) {
        postponeMessageRepository.delete(postponeMessage);
    }

    public List<PostponeMessage> getAll() {
        return postponeMessageRepository.findAll();
    }

    public PostponeMessage getLastByViberId(String viberId) {
        return postponeMessageRepository.findByViberId(viberId).stream().filter(p -> p.getIsLast()).findAny().orElse(null);
    }
}
