package com.news.controller;

import com.news.controller.request.SearchRequest;
import com.news.controller.response.PageResult;
import com.news.controller.response.SearchResponse;
import com.news.service.WebQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/webs")
public class WebSearchController {
    private final WebQueryService webQueryService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest request){
        return webQueryService.search(request.getQuery(), request.getPage(), request.getSize());
    }
}
