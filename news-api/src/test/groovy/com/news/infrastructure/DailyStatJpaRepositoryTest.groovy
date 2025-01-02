package com.news.infrastructure

import com.news.dailystat.infrastructure.DailyStatEntity
import com.news.dailystat.infrastructure.DailyStatJpaRepository
import com.news.dailystat.infrastructure.DailyStatRepositoryImpl
import com.news.dailystat.model.DailyStat
import com.news.dailystat.service.port.DailyStatRepository
import com.news.feign.NaverClient
import com.news.search.infrastructure.WebRepositoryImpl
import jakarta.persistence.EntityManager
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ActiveProfiles("test")
@SpringBootTest
class DailyStatJpaRepositoryTest extends Specification {
    @Autowired
    EntityManager entityManager

    @Autowired
    DailyStatRepositoryImpl dailyStatRepository;

    @Autowired
    DailyStatJpaRepository dailyStatJpaRepository;

    @SpringBean
    NaverClient naverClient = Mock()


    def "저장이 된다."() {
        given:
        def givenQuery = "HTTP"

        when:
        def dailyStat = DailyStat.create(givenQuery, LocalDateTime.now())
        def saved = dailyStatRepository.save(dailyStat)


        then:
        verifyAll {
            saved.query == givenQuery
        }
    }

    def "쿼리의 카운트를 조회한다."() {
        given:
        def givenQuery = 'HTTP'
        def now = LocalDateTime.of(2024, 5, 1, 0,0,0)
        def nowLocalDate = LocalDate.of(2024, 5, 1)
        def stat1 = DailyStat.create(givenQuery, now.plusMinutes(10))
        def stat2 = DailyStat.create(givenQuery, now.minusMinutes(1))
        def stat3 = DailyStat.create(givenQuery, now.plusMinutes(10))
        def stat4 = DailyStat.create('JAVA', now.plusMinutes(10))

        dailyStatRepository.save(stat1)
        dailyStatRepository.save(stat2)
        dailyStatRepository.save(stat3)
        dailyStatRepository.save(stat4)

        when:
        def result = dailyStatRepository.countByQueryAndMonthly(givenQuery, now, now.plusMonths(1))

        then:
        result == 2
    }

    def "벌크 쿼리의 카운트를 조회한다."() {
        given:
        def givenQuery = 'HTTP'
        def now = LocalDateTime.of(2024, 5, 1, 0,0,0)
        def stat1 = DailyStat.create(givenQuery, now.plusMinutes(10))
        def stat2 = DailyStat.create(givenQuery, now.minusMinutes(1))
        def stat3 = DailyStat.create(givenQuery, now.plusMinutes(10))
        def stat4 = DailyStat.create('JAVA', now.plusMinutes(10))


        dailyStatRepository.saveAll([stat1, stat2, stat3, stat4])

        when:
        def result = dailyStatRepository.countByQueryAndMonthly(givenQuery, now, now.plusMonths(1))

        then:
        result == 2
    }

    def "JPA 쿼리의 카운트를 조회한다."() {
        given:
        def givenQuery = 'HTTP'
        def now = LocalDateTime.of(2024, 5, 1, 0,0,0)
        def stat1 = DailyStat.create(givenQuery, now.plusMinutes(10))
        def stat2 = DailyStat.create(givenQuery, now.minusMinutes(1))
        def stat3 = DailyStat.create(givenQuery, now.plusMinutes(10))
        def stat4 = DailyStat.create('JAVA', now.plusMinutes(10))


        dailyStatJpaRepository.saveAll([DailyStatEntity.from(stat1), DailyStatEntity.from(stat2), DailyStatEntity.from(stat3), DailyStatEntity.from(stat4)])

        when:
        def result = dailyStatJpaRepository.countByQueryAndEventDateTimeBetween(givenQuery, now, now.plusDays(1))

        then:
        result == 2
    }
}
