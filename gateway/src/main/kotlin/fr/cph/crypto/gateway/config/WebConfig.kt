package fr.cph.crypto.gateway.config

import fr.cph.crypto.gateway.GatewayResponseErrorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.web.client.RestTemplate

@Configuration
class WebConfig {

    @Autowired
    private lateinit var backendProperties: BackendProperties

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
        restTemplate.interceptors.add(BasicAuthorizationInterceptor(backendProperties.security.jwt.clientId, backendProperties.security.jwt.clientSecret))
        return restTemplate
    }
}