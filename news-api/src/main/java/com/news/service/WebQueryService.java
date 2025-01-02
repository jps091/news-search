package com.news.service;

import com.news.controller.response.PageResult;
import com.news.controller.response.SearchResponse;
import com.news.service.port.WebRepository;
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
