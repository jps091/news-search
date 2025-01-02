package com.news.dailystat.service;

import com.news.dailystat.infrastructure.DailyStatJpaRepository;
import com.news.search.controller.response.StatResponse;
import com.news.dailystat.service.port.DailyStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class DailyStatQueryService {

    private final DailyStatRepository dailyStatRepository;
    private final DailyStatJpaRepository dailyStatJpaRepository;

    @Transactional(readOnly = true)
    public StatResponse findQueryCount(String query, LocalDate localDate){
        long count = dailyStatRepository.countByQueryAndMonthly(
                query,
                localDate.withDayOfMonth(1).atStartOfDay(),
                localDate.withDayOfMonth(localDate.lengthOfMonth()).atTime(LocalTime.MAX)
        );
        return new StatResponse(query, count);
    }

    @Transactional(readOnly = true)
    public StatResponse findQueryCountByJpa(String query, LocalDate localDate){
        long count = dailyStatJpaRepository.countByQueryAndEventDateTimeBetween(
                query,
                localDate.withDayOfMonth(1).atStartOfDay(),
                localDate.withDayOfMonth(localDate.lengthOfMonth()).atTime(LocalTime.MAX)
        );
        return new StatResponse(query, count);
    }
}
