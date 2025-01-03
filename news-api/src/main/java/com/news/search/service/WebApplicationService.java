package com.news.search.service;

import com.news.dailystat.service.response.DailyStatQueryResponse;
import com.news.search.controller.response.StatResponse;
import com.news.dailystat.model.DailyStat;
import com.news.dailystat.service.DailyStatCommandService;
import com.news.dailystat.service.DailyStatQueryService;
import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;
import com.news.search.service.response.PageQueryResult;
import com.news.search.service.response.SearchQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebApplicationService {
    private final WebQueryService webQueryService;
    private final DailyStatCommandService dailyStatCommandService;
    private final DailyStatQueryService dailyStatQueryService;

    public PageResult<SearchResponse> search(String query, int page, int size){
        PageQueryResult<SearchQueryResponse> pageQueryResponse = webQueryService.search(query, page, size);
        PageResult<SearchResponse> pageResponse = convertToPageResult(pageQueryResponse);
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.save(dailyStat);
        return pageResponse;
    }

    public PageResult<SearchResponse> searchByJpa(String query, int page, int size){
        PageQueryResult<SearchQueryResponse> pageQueryResponse = webQueryService.search(query, page, size);
        PageResult<SearchResponse> pageResponse = convertToPageResult(pageQueryResponse);
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.saveByJpa(dailyStat);
        return pageResponse;
    }

    public Long create(String query){
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.save(dailyStat);
        return dailyStat.getId();
    }

    public Long createByJpa(String query){
        DailyStat dailyStat = DailyStat.create(query, LocalDateTime.now());
        dailyStatCommandService.saveByJpa(dailyStat);
        return dailyStat.getId();
    }

    public StatResponse findQueryCount(String query, LocalDate date) {
        DailyStatQueryResponse queryResponse = dailyStatQueryService.findQueryCount(query, date);
        return new StatResponse(queryResponse.query(), queryResponse.count());
    }

    public StatResponse findQueryCountByJpa(String query, LocalDate date) {
        DailyStatQueryResponse queryResponse = dailyStatQueryService.findQueryCountByJpa(query, date);
        return new StatResponse(queryResponse.query(), queryResponse.count());
    }

    public List<StatResponse> findTop5Query() {
        List<DailyStatQueryResponse> queryResponse = dailyStatQueryService.findTop5Query();
        return queryResponse.stream()
                .map(this::toStatResponse)
                .toList();
    }

    public List<StatResponse> findTop5QueryByJpa() {
        List<DailyStatQueryResponse> queryResponse = dailyStatQueryService.findTop5QueryByJpa();
        return queryResponse.stream()
                .map(this::toStatResponse)
                .toList();
    }

    private PageResult<SearchResponse> convertToPageResult(PageQueryResult<SearchQueryResponse> pageQueryResponse) {
        List<SearchResponse> searchResponses = pageQueryResponse.contents().stream()
                .map(this::toSearchResponse)
                .toList();
        return new PageResult<>(pageQueryResponse.page(), pageQueryResponse.size(), pageQueryResponse.totalElements(), searchResponses);
    }

    private SearchResponse toSearchResponse(SearchQueryResponse queryResponse){
        return new SearchResponse(queryResponse.title(), queryResponse.link(), queryResponse.description());
    }

    private StatResponse toStatResponse(DailyStatQueryResponse queryResponse){
        return new StatResponse(queryResponse.query(), queryResponse.count());
    }
}
