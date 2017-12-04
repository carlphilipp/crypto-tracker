package fr.cph.crypto.backend.proxy

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.web.client.RestTemplate


@Configuration
@SpringBootApplication
class GatewayApplication {

    @Qualifier(value = "restTemplate")
    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactory())
        restTemplate.errorHandler = GatewayResponseErrorHandler()
        return restTemplate
    }

    @Qualifier(value = "restTemplateAuth")
    @Bean
    fun restTemplateAuth(): RestTemplate {
        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactory())
        restTemplate.errorHandler = GatewayResponseErrorHandler()
        restTemplate.interceptors.add(BasicAuthorizationInterceptor("testjwtclientid", "XY7kmzoNzl100"))
        return restTemplate
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(GatewayApplication::class.java, *args)
}

