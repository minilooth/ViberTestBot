package by.autocapital.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.autocapital.models.PostponeMessage;
import by.autocapital.repositories.PostponeMessageRepository;

@Service
public class PostponeMessageService {
    @Autowired
    private PostponeMessageRepository postponeMessageRepository;

    @Transactional
    public void save(PostponeMessage postponeMessage) {
        postponeMessageRepository.save(postponeMessage);
    }

    @Transactional
    public void delete(PostponeMessage postponeMessage) {
        postponeMessageRepository.delete(postponeMessage);
    }

    @Transactional
    public List<PostponeMessage> getAll() {
        return postponeMessageRepository.findAll();
    }

    @Transactional
    public List<PostponeMessage> getAllWithoutLast() {
        return getAll().stream().filter(m -> !m.getIsLast()).collect(Collectors.toList());
    }

    @Transactional
    public PostponeMessage getLastByViberId(String viberId) {
        return postponeMessageRepository.findByViberId(viberId).stream().filter(p -> p.getIsLast()).findAny().orElse(null);
    }
}
