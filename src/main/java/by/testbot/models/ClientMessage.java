package by.testbot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_message")
public class ClientMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "ViberId", nullable = false)
    private String viberId;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Model")
    private String model;

    @Column(name = "YearOfIssueFrom")
    private Integer yearOfIssueFrom;

    @Column(name = "YearOfIssueTo")
    private Integer yearOfIssueTo;

    @Column(name = "Step1")
    private String step1;

    @Column(name = "Step2")
    private String step2;

    @Column(name = "Step3")
    private String step3;
    
    @Column(name = "Step4")
    private String step4;

    @Column(name = "Step5")
    private String step5;

    @Column(name = "Step6")
    private String step6;

    // @Column(name = "Step7")
    // private String step7;

    @Column(name = "DialogIsOver", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean dialogIsOver;
}
