package com.news.dailystat.service;

import com.news.dailystat.infrastructure.DailyStatEntity;
import com.news.dailystat.infrastructure.DailyStatJpaRepository;
import com.news.dailystat.model.DailyStat;
import com.news.dailystat.service.port.DailyStatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DailyStatCommandService {

    private final DailyStatRepository dailyStatRepository;
    private final DailyStatJpaRepository dailyStatJpaRepository;

    @Transactional
    public void save(DailyStat dailyStat) {
        log.info("save daily stats: {}", dailyStat);
        dailyStatRepository.save(dailyStat);
    }

    @Transactional
    public void saveByJpa(DailyStat dailyStat) {
        log.info("save daily stats: {}", dailyStat);
        dailyStatJpaRepository.save(DailyStatEntity.from(dailyStat));
    }
}
