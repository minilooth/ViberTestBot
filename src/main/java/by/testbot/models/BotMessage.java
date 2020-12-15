package by.testbot.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Table
@Entity(name = "bot_message")
@ToString(exclude = { "managers", "buttons", "dialogues", "nextMessage", "nextMessages", "previousMessage", "previousMessages" })
@EqualsAndHashCode(exclude = { "managers", "buttons", "dialogues", "nextMessage", "nextMessages", "previousMessage", "previousMessages" })
public class BotMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Message", nullable = false, columnDefinition = "LONGTEXT")
    private String message;

    @Column(name = "IsAdded", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isAdded;

    @Column(name = "LastUpdate", nullable = false)
    private Long lastUpdate;

    @ManyToOne
    @JoinColumn(name = "NextMessageId")
    private BotMessage nextMessage;

    @OneToMany(mappedBy = "nextMessage")
    private Set<BotMessage> nextMessages;

    @ManyToOne
    @JoinColumn(name = "PreviousMessageId")
    private BotMessage previousMessage;

    @OneToMany(mappedBy = "previousMessage")
    private Set<BotMessage> previousMessages;

    @OneToMany(mappedBy = "botMessage")
    private Set<Manager> managers;

    @OneToMany(mappedBy = "botMessage")
    private Set<Dialogue> dialogues;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "bot_message_button", 
               joinColumns = @JoinColumn(name = "BotMessageId"), 
               inverseJoinColumns = @JoinColumn(name = "ButtonId"))
    private Set<Button> buttons;
}
