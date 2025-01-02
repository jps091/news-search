package com.news.service

import com.news.search.service.WebQueryService
import com.news.search.service.port.WebRepository
import spock.lang.Specification

class WebQueryServiceTest extends Specification {
    WebRepository webRepository = Mock()
    WebQueryService webQueryService

    void setup(){
        webQueryService = new WebQueryService(webRepository)
    }

    def "search시 인자가 그대로 넘어간다."(){
        given:
        def givenQuery = "test news keyword"
        def givenPage = 1
        def givenSize = 10

        when:
        webQueryService.search(givenQuery, givenPage, givenSize)

        then:
        1 * webRepository.search(*_) >> {
            String query, int page, int size ->
                assert query == givenQuery
                assert page == givenPage
                assert size == givenSize
        }
    }
}
