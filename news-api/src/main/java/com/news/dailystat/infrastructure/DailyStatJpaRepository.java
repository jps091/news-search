package com.news.dailystat.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DailyStatJpaRepository extends JpaRepository<DailyStatEntity, Long> {
    long countByQueryAndEventDateTimeBetween(String query, LocalDateTime start, LocalDateTime end);
}
