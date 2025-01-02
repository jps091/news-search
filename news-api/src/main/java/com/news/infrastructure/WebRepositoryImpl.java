package com.news.infrastructure;

import com.news.controller.response.PageResult;
import com.news.controller.response.SearchResponse;
import com.news.feign.NaverClient;
import com.news.model.Item;
import com.news.model.NaverWebResponse;
import com.news.service.port.WebRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class WebRepositoryImpl implements WebRepository {
    private final NaverClient naverClient;

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        NaverWebResponse response = naverClient.search(query, page, size);
        List<SearchResponse> responses = response.items().stream()
                .map(this::convertToSearchResponse)
                .toList();
        return new PageResult<>(page, size, response.total(), responses);
    }

    private SearchResponse convertToSearchResponse(Item item){
        return SearchResponse.builder()
                .title(item.title())
                .link(item.link())
                .description(item.description())
                .build();
    }
}
