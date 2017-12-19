package fr.cph.crypto.gateway

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
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

    @Value("\${backend.security.jwt.client-id}")
    private lateinit var clientId: String

    @Value("\${backend.security.jwt.client-secret}")
    private lateinit var clientSecret: String

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
        restTemplate.interceptors.add(BasicAuthorizationInterceptor(clientId, clientSecret))
        return restTemplate
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(GatewayApplication::class.java, *args)
}

