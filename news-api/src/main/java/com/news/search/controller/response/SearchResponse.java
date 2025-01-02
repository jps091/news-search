package com.news.search.controller.response;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record SearchResponse(
        String title,
        String link,
        String description
) {
}
