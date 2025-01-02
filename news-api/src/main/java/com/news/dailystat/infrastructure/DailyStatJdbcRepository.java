package com.news.dailystat.infrastructure;

import com.news.dailystat.model.DailyStat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class DailyStatJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public int save(DailyStat dailyStat) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("daily_stat")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("query", dailyStat.getQuery());
        parameters.put("event_date_time", dailyStat.getEventDateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        log.info("key={}", key.longValue());
        return key.intValue();
    }

    public long countByQueryAndEventDateTimeBetween(String query, LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT COUNT(*) FROM daily_stat WHERE query = ? AND event_date_time BETWEEN ? AND ?";
        return jdbcTemplate.queryForObject(sql, Long.class, query, start, end);
    }

    public void saveAll(List<DailyStat> dailyStats) {
        StringBuilder sql = new StringBuilder("INSERT INTO daily_stat (query, event_date_time) VALUES ");
        List<Object> params = new ArrayList<>();

        for (DailyStat dailyStat : dailyStats) {
            sql.append("(?, ?),");
            params.add(dailyStat.getQuery());
            params.add(Timestamp.valueOf(dailyStat.getEventDateTime()));
        }

        // 마지막 콤마 제거
        sql.deleteCharAt(sql.length() - 1);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }
}
