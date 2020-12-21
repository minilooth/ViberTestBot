package by.testbot.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import by.testbot.models.enums.AnswerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "button")
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Text", nullable = false)
    private String text;

    @Column(name = "AnswerType")
    @Enumerated(EnumType.ORDINAL)
    private AnswerType answerType;

    @Column(name = "DialogueEnds", columnDefinition = "TINYINT(1)")
    private Boolean dialogueEnds;

    @Column(name = "CallbackData", nullable = false)
    private String callbackData;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "button")
    private Set<Manager> managers;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "BotMessageId")
    private BotMessage botMessage;
}
