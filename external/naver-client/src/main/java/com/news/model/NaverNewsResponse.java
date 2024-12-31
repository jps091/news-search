package com.news.model;

import java.util.List;

public record NaverNewsResponse(String lastBuildDate, int total, int start, int display, List<Item> items) {
}
