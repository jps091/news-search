package com.news.feign


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@SpringBootTest(classes = NaverClientIntegrationTest.TestConfig.class)
@ActiveProfiles("test")
class NaverClientIntegrationTest extends Specification {

    @EnableAutoConfiguration
    @EnableFeignClients(clients = NaverClient.class)
    static class TestConfig{
    }

    @Autowired
    NaverClient naverClient

    def "naver api 호출"(){
        given:
        when:
        def response = naverClient.search("java", 1, 5)

        then:
        print response
    }
}
