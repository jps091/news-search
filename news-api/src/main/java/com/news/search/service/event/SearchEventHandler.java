package com.news.search.service.event;

import com.news.dailystat.model.DailyStat;
import com.news.dailystat.service.DailyStatCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchEventHandler {

    private final DailyStatCommandService dailyStatCommandService;
    @Async
    @EventListener
    public void handleEvent(SearchEvent event){
        log.info("[SearchEventHandler] handleEvent: {}", event);
        DailyStat dailyStat = DailyStat.create(event.query(), event.timestamp());
        dailyStatCommandService.save(dailyStat);
    }
}
