package com.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;


public record Item(
        String title,
        String link,
        String description
) {}
