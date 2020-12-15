package by.testbot.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "manager")
@ToString(exclude = { "user", "botMessage", "buttons" })
@EqualsAndHashCode(exclude = { "user", "botMessage", "buttons" })
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Firstname")
    private String firstname;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "BotMessageText", columnDefinition = "LONGTEXT")
    private String botMessageText;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BotMessageId")
    private BotMessage botMessage;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "manager_button", 
               joinColumns = @JoinColumn(name = "ManagerId"), 
               inverseJoinColumns = @JoinColumn(name = "ButtonId"))
    private Set<Button> buttons;
}
