package by.autocapital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.Answer;
import by.autocapital.models.Dialogue;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public List<Answer> findAllByDialogue(Dialogue dialogue);
    public Answer findByDialogueAndIsLast(Dialogue dialogue, Boolean isLast);
}
