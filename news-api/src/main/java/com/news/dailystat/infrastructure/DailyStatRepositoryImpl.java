package com.news.dailystat.infrastructure;

import com.news.dailystat.model.DailyStat;
import com.news.dailystat.service.port.DailyStatRepository;
import com.news.exception.ApiException;
import com.news.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DailyStatRepositoryImpl implements DailyStatRepository {

    private final DailyStatJpaRepository dailyStatJpaRepository;
    private final DailyStatJdbcRepository dailyStatJdbcRepository;

    @Override
    public DailyStat save(DailyStat dailyStat) {
        int save = dailyStatJdbcRepository.save(dailyStat);
        if(save <= 0){
            throw new  ApiException("저장 실패", ErrorType.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
        }
        return dailyStat;
    }

    @Override
    public long countByQueryAndMonthly(String query, LocalDateTime start, LocalDateTime end) {
        return dailyStatJdbcRepository.countByQueryAndEventDateTimeBetween(query, start, end);
    }

    public void saveAll(List<DailyStat> dailyStats){
        dailyStatJdbcRepository.saveAll(dailyStats);
    }
}
