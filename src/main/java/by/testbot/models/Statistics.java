package by.testbot.models;

import java.time.LocalDate;

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
@Entity
@Table(name = "statistics")
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "MidnightToEightOclockCount", nullable = false)
    private Long midnightToEightOclickCount;

    @Column(name = "EightOclockToSixteenOclockCount", nullable = false)
    private Long eightOclockToSixteenOclockCount;

    @Column(name = "SixteenOclockToMidnightCount", nullable = false)
    private Long sixteenOclickToMidnightCount;
}
