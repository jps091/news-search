package com.news.dailystat.service.port;

import com.news.dailystat.model.DailyStat;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyStatRepository {
    DailyStat save(DailyStat dailyStat);
    long countByQueryAndMonthly(String query, LocalDateTime start, LocalDateTime end);

    void saveAll(List<DailyStat> dailyStats);
}
