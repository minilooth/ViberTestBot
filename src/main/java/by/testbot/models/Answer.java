package by.testbot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import by.testbot.models.enums.AnswerType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "answer")
@ToString(exclude = { "dialogue" })
@EqualsAndHashCode(exclude = { "dialogue" })
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Answer", nullable = false)
    private String answer;

    @Column(name = "Type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AnswerType answerType;

    @Column(name = "Step", nullable = false)
    private Integer step;

    @Column(name = "Timestamp", nullable = false)
    private Long timestamp;

    @Column(name = "IsLast", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isLast;

    @ManyToOne
    @JoinColumn(name = "DialogueId", nullable = false)
    private Dialogue dialogue;
}
