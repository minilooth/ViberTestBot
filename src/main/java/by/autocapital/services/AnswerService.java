package by.autocapital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.autocapital.models.Answer;
import by.autocapital.models.Dialogue;
import by.autocapital.repositories.AnswerRepository;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    @Transactional
    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    @Transactional
    public List<Answer> getAll() {
        return answerRepository.findAll();
    }

    @Transactional
    public List<Answer> getAllByDialogue(Dialogue dialogue) {
        return answerRepository.findAllByDialogue(dialogue);
    }

    @Transactional
    public Answer getLastByDialogue(Dialogue dialogue) {
        return answerRepository.findByDialogueAndIsLast(dialogue, true);
    }
}
