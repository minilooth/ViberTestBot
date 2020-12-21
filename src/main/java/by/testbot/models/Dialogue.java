package by.testbot.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "dialogue")
public class Dialogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Model")
    private String model;

    @Column(name = "YearFrom")
    private Integer yearFrom;

    @Column(name = "YearTo")
    private Integer yearTo;

    @Column(name = "DialogueIsOver", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean dialogueIsOver;

    @Column(name = "LastUpdate", nullable = false)
    private Long lastUpdate;

    @Column(name = "BotMessagesLastUpdate", columnDefinition = "BIGINT DEFAULT 0")
    private Long botMessagesLastUpdate;

    @Column(name = "MustBeAnswers", nullable = false)
    private Integer mustBeAnswers;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "BotMessageId")
    private BotMessage botMessage;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ClientId", nullable = false)
    private Client client;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "dialogue", cascade = CascadeType.REMOVE)
    private Set<Answer> answers;
}
