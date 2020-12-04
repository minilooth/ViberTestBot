package by.testbot.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@ToString(exclude = { "client" })
@EqualsAndHashCode(exclude = { "client" })
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

    @Column(name = "Step1Answer")
    private String step1Answer;

    @Column(name = "Step2Answer")
    private String step2Answer;

    @Column(name = "Step3Answer")
    private String step3Answer;
    
    @Column(name = "Step4Answer")
    private String step4Answer;

    @Column(name = "Step5Answer")
    private String step5Answer;

    @Column(name = "Step6Answer")
    private String step6Answer;

    @Column(name = "DialogIsOver", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean dialogIsOver;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ClientId", nullable = false)
    private Client client;
}
