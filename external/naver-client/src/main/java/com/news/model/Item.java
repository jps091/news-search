package com.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Item(
        String title,
        @JsonProperty("originallink") String originalLink,
        String link,
        String description,
        String pubDate
) {}
