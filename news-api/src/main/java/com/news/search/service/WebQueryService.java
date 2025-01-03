package com.news.search.service;

import com.news.search.infrastructure.WebRepository;
import com.news.search.service.response.PageQueryResult;
import com.news.search.service.response.SearchQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebQueryService {

    private final WebRepository webRepository;

    public PageQueryResult<SearchQueryResponse> search(String query, int page, int size) {
        return webRepository.search(query, page, size);
    }
}
