package com.news.search.controller;

import com.news.search.controller.request.SearchRequest;
import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;
import com.news.search.controller.response.StatResponse;
import com.news.search.service.WebApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/test")
public class TestController {
    private final WebApplicationService webApplicationService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest request){
        return webApplicationService.search(request.getQuery(), request.getPage(), request.getSize());
    }

    @GetMapping("/jpa")
    public PageResult<SearchResponse> searchByJpa(@Valid SearchRequest request){
        return webApplicationService.searchByJpa(request.getQuery(), request.getPage(), request.getSize());
    }

    @GetMapping("/stats")
    public StatResponse findQueryStats(@RequestParam(name = "query") String query,
                                       @RequestParam(name = "date", required = false)
                                       LocalDate date) {
        LocalDate localDate = (date != null) ? date : LocalDate.now();
        return webApplicationService.findQueryCount(query, localDate);
    }

    @GetMapping("/jpa/stats")
    public StatResponse findQueryStatsByJpa(@RequestParam(name = "query") String query,
                                       @RequestParam(name = "date", required = false)
                                       LocalDate date) {
        LocalDate localDate = (date != null) ? date : LocalDate.now();
        return webApplicationService.findQueryCountByJpa(query, localDate);
    }

    @PostMapping
    public Long create(@RequestParam(name = "query") String query){
        return webApplicationService.create(query);
    }

    @PostMapping("/jpa")
    public Long createByJpa(@RequestParam(name = "query") String query){
        return webApplicationService.createByJpa(query);
    }

    @GetMapping("/stats/ranking")
    public List<StatResponse> findTop5Stats() {
        return webApplicationService.findTop5Query();
    }

    @GetMapping("/jpa/stats/ranking")
    public List<StatResponse> findTop5StatsByJpa() {
        return webApplicationService.findTop5QueryByJpa();
    }
}
