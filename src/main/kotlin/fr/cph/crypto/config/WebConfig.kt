package fr.cph.crypto.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate

@Configuration
class WebConfig {

    @Bean
    @Primary
    fun objectMapper() = ObjectMapper().apply {
        registerModule(KotlinModule())
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
