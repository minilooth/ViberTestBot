package by.autocapital.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    public Statistics findFirstByDate(LocalDate date);
}
