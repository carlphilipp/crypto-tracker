package fr.cph.crypto.proxy

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.web.client.RestTemplate


@Configuration
@SpringBootApplication
class FrontEndProxy {

    @Qualifier(value = "restTemplate")
    @Bean
    fun restTemplate()  = RestTemplate()

    @Qualifier(value = "restTemplateAuth")
    @Bean
    fun restTemplateAuth(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(BasicAuthorizationInterceptor("testjwtclientid", "XY7kmzoNzl100"))
        return restTemplate
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(FrontEndProxy::class.java, *args)
}

