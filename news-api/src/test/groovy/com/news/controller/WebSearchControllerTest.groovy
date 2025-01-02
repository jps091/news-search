package com.news.controller

import com.news.service.WebQueryService
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class WebSearchControllerTest extends Specification {
    WebQueryService webQueryService = Mock()
    WebSearchController webController
    MockMvc mockMvc

    void setup(){
        webController = new WebSearchController(webQueryService)
        mockMvc = MockMvcBuilders.standaloneSetup(webController).build()
    }

    def "search"(){
        given:
        def givenQuery = "test news keyword"
        def givenPage = 1
        def givenSize = 10

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/webs?query=${givenQuery}&size=${givenSize}&page=${givenPage}"))
                .andReturn()
                .response

        then:
        response.status == HttpStatus.OK.value()

        and:
        1 * webQueryService.search(*_) >> {
            String query, int page, int size ->
                assert query == givenQuery
                assert page == givenPage
                assert size == givenSize
        }
    }
}
