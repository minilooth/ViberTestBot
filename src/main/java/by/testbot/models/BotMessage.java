package by.testbot.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Table
@Entity(name = "bot_message")
@ToString(exclude = { "managers" })
@EqualsAndHashCode(exclude = { "managers" })
public class BotMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Message", nullable = false, columnDefinition = "LONGTEXT")
    private String message;

    @OneToMany(mappedBy = "botMessage")
    private Set<Manager> managers;
}
