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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import by.testbot.models.enums.AnswerType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "button")
@ToString(exclude = { "managers", "botMessages" })
@EqualsAndHashCode(exclude = { "managers", "botMessages" })
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Text")
    private String text;

    @Column(name = "AnswerType")
    @Enumerated(EnumType.ORDINAL)
    private AnswerType answerType;

    @Column(name = "DialogueEnds", columnDefinition = "TINYINT(1)")
    private Boolean dialogueEnds;

    @Column(name = "IsLast", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isLast;

    @Column(name = "CallbackData", nullable = false)
    private String callbackData;

    @ManyToMany
    @JoinTable(name = "manager_button", 
               joinColumns = {@JoinColumn(name = "ButtonId", referencedColumnName = "Id")}, 
               inverseJoinColumns = {@JoinColumn(name = "ManagerId", referencedColumnName = "Id")})
    private Set<Manager> managers;

    @ManyToMany
    @JoinTable(name = "bot_message_button", 
               joinColumns = {@JoinColumn(name = "ButtonId", referencedColumnName = "Id")}, 
               inverseJoinColumns = {@JoinColumn(name = "BotMessageId", referencedColumnName = "Id")})
    private Set<BotMessage> botMessages;
}
