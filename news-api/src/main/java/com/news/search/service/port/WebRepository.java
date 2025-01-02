package com.news.search.service.port;

import com.news.search.controller.response.PageResult;
import com.news.search.controller.response.SearchResponse;

public interface WebRepository {
    PageResult<SearchResponse> search(String query, int page, int size);
}
