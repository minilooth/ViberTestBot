// package by.testbot.models;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;

// import lombok.Data;

// @Data
// @Entity
// @Table(name = "order")
// public class Order {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "Id", nullable = false)
//     private Long id;

//     @Column(name = "ClientId", nullable = false)
//     private Long clientId;

//     @Column(name = "Brand")
//     private String brand;

//     @Column(name = "Model")
//     private String model;

//     @Column(name = "YearFrom")
//     private Integer yearFrom;

//     @Column(name = "YearTo")
//     private Integer yearTo;

//     @Column(name = "Step1Answer")
//     private String step1Answer;

//     @Column(name = "Step2Answer")
//     private String step2Answer;

//     @Column(name = "Step3Answer")
//     private String step3Answer;

//     @Column(name = "Step4Answer")
//     private String step4Answer;

//     @Column(name = "Step5Answer")
//     private String step5Answer;

//     @Column(name = "Step6Answer")
//     private String step6Answer;

//     @Column(name = "Step7Answer")
//     private String step7Answer;

//     @Column(name = "DialogIsOver", nullable = false, columnDefinition = "TINYINT(1)")
//     private Boolean dialogIsOver;
// }
