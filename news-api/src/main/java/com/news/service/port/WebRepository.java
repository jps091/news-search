package com.news.service.port;

import com.news.controller.response.PageResult;
import com.news.controller.response.SearchResponse;

public interface WebRepository {
    PageResult<SearchResponse> search(String query, int page, int size);
}
