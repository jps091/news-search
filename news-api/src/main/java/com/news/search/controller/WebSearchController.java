package com.news.search.controller;

import com.news.search.controller.request.SearchRequest;
import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;
import com.news.search.controller.response.StatResponse;
import com.news.search.service.WebApplicationService;
import com.news.search.service.WebQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/webs")
public class WebSearchController {
    private final WebApplicationService webApplicationService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest request){
        return webApplicationService.search(request.getQuery(), request.getPage(), request.getSize());
    }

    @GetMapping("/stats")
    public StatResponse findQueryStats(@RequestParam(name = "query") String query,
                                       @RequestParam(name = "date", required = false)
                                       LocalDate date) {
        log.info("[WebSearchController] find stats query={}, date={}", query, date);
        LocalDate localDate = (date != null) ? date : LocalDate.now();
        return webApplicationService.findQueryCount(query, localDate);
    }
}
