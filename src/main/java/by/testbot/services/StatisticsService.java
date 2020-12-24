package by.testbot.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.Statistics;
import by.testbot.repositories.StatisticsRepository;

@Service
public class StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;

    @Transactional
    public void save(Statistics statistics) {
        statisticsRepository.save(statistics);
    }

    @Transactional
    public void delete(Statistics statistics) {
        statisticsRepository.delete(statistics);
    }

    @Transactional
    public List<Statistics> getAll() {
        return statisticsRepository.findAll();
    }

    @Transactional
    public Statistics getYesterdayStatistics() {
        return statisticsRepository.findFirstByDate(LocalDate.now().minus(1, ChronoUnit.DAYS));
    }

    @Transactional
    public Statistics getTodayStatistics() {
        Statistics statistics = statisticsRepository.findFirstByDate(LocalDate.now());
        
        if (statistics == null) {
            statistics = createTodayStatistics();
        }

        return statistics;
    }

    private Statistics createTodayStatistics() {
        Statistics statistics = Statistics.builder()
                                          .date(LocalDate.now())
                                          .midnightToEightOclickCount(0L)
                                          .eightOclockToSixteenOclockCount(0L)
                                          .sixteenOclickToMidnightCount(0L)
                                          .build();

        save(statistics);

        return statistics;
    }
}
