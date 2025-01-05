package com.news.search.service;

import com.news.dailystat.service.response.DailyStatQueryResponse;
import com.news.search.controller.response.StatResponse;
import com.news.dailystat.service.DailyStatQueryService;
import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;
import com.news.search.service.event.SearchEvent;
import com.news.search.service.response.PageQueryResult;
import com.news.search.service.response.SearchQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebApplicationService {
    private final WebQueryService webQueryService;
    private final DailyStatQueryService dailyStatQueryService;
    private final ApplicationEventPublisher eventPublisher;

    public PageResult<SearchResponse> search(String query, int page, int size){
        PageQueryResult<SearchQueryResponse> pageQueryResponse = webQueryService.search(query, page, size);
        if(isNotEmptyQueryResponse(pageQueryResponse)){
            log.info("검색결과 개수: {}", pageQueryResponse.size());
            eventPublisher.publishEvent(new SearchEvent(query, LocalDateTime.now()));
        }
        return convertToPageResult(pageQueryResponse);
    }

    public StatResponse findQueryCount(String query, LocalDate date) {
        DailyStatQueryResponse queryResponse = dailyStatQueryService.findQueryCount(query, date);
        return new StatResponse(queryResponse.query(), queryResponse.count());
    }

    public List<StatResponse> findTop5Query() {
        List<DailyStatQueryResponse> queryResponse = dailyStatQueryService.findTop5Query();
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

    private static boolean isNotEmptyQueryResponse(PageQueryResult<SearchQueryResponse> pageQueryResponse) {
        return !pageQueryResponse.contents().isEmpty();
    }
}
