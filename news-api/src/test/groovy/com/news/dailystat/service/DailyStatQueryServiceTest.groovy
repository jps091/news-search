package com.news.dailystat.service

import com.news.dailystat.service.port.DailyStatRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class DailyStatQueryServiceTest extends Specification {

    DailyStatQueryService dailyStatQueryService

    DailyStatRepository dailyStatRepository = Mock(DailyStatRepository)

    void setup() {
        dailyStatQueryService = new DailyStatQueryService(dailyStatRepository)
    }

    def "findQueryCount 조회시 한달치를 조회하면서 쿼리개수가 반환된다."() {
        given:
        def givenQuery = 'HTTP'
        def givenDate = LocalDate.of(2025, 1, 1)
        def expectedCount = 2

        when:
        def response = dailyStatQueryService.findQueryCount(givenQuery, givenDate)

        then:
        1 * dailyStatRepository.countByQueryAndMonthly(
                givenQuery,
                LocalDateTime.of(2025, 1, 1, 0, 0,0),
                LocalDateTime.of(2025, 1, 31, 23, 59,59, 999999999),
        ) >> expectedCount

        and:
        response.count() == expectedCount
    }
}
