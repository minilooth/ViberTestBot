package by.testbot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.Answer;
import by.testbot.models.Dialogue;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public List<Answer> findAllByDialogue(Dialogue dialogue);

    public Answer findByDialogueAndIsLast(Dialogue dialogue, Boolean isLast);
}
