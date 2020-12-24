package by.testbot.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import by.testbot.models.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    public Statistics findFirstByDate(LocalDate date);
}
