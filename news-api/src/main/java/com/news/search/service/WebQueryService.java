package com.news.search.service;

import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;
import com.news.search.service.port.WebRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebQueryService {

    private final WebRepository webRepository;

    public PageResult<SearchResponse> search(String query, int page, int size) {
        return webRepository.search(query, page, size);
    }
}
