package com.news.search.infrastructure;

import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;
import com.news.feign.NaverClient;
import com.news.model.Item;
import com.news.model.NaverWebResponse;
import com.news.search.service.response.PageQueryResult;
import com.news.search.service.response.SearchQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class WebRepository {
    private final NaverClient naverClient;

    public PageQueryResult<SearchQueryResponse> search(String query, int page, int size) {
        NaverWebResponse response = naverClient.search(query, page, size);
        List<SearchQueryResponse> responses = response.items().stream()
                .map(this::convertToSearchResponse)
                .toList();
        return new PageQueryResult<>(page, size, response.total(), responses);
    }

    private SearchQueryResponse convertToSearchResponse(Item item){
        return SearchQueryResponse.builder()
                .title(item.title())
                .link(item.link())
                .description(item.description())
                .build();
    }
}
