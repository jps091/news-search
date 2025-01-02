package com.news.search.service;

import com.news.search.controller.response.StatResponse;
import com.news.dailystat.model.DailyStat;
import com.news.dailystat.service.DailyStatCommandService;
import com.news.dailystat.service.DailyStatQueryService;
import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebApplicationService {
    private final WebQueryService webQueryService;
    private final DailyStatCommandService dailyStatCommandService;
    private final DailyStatQueryService dailyStatQueryService;

    @Transactional
    public PageResult<SearchResponse> search(String query, int page, int size){
        PageResult<SearchResponse> response = webQueryService.search(query, page, size);
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.save(dailyStat);
        return response;
    }

    @Transactional
    public PageResult<SearchResponse> searchByJpa(String query, int page, int size){
        PageResult<SearchResponse> response = webQueryService.search(query, page, size);
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.saveByJpa(dailyStat);
        return response;
    }

    @Transactional
    public Long create(String query){
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.save(dailyStat);
        return dailyStat.getId();
    }

    @Transactional
    public Long createByJpa(String query){
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.saveByJpa(dailyStat);
        return dailyStat.getId();
    }

    @Transactional(readOnly = true)
    public StatResponse findQueryCount(String query, LocalDate date) {
        return dailyStatQueryService.findQueryCount(query, date);
    }

    @Transactional(readOnly = true)
    public StatResponse findQueryCountByJpa(String query, LocalDate date) {
        return dailyStatQueryService.findQueryCountByJpa(query, date);
    }
}
