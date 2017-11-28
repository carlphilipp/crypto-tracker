package fr.cph.crypto.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.web.client.RestTemplate

@Configuration
class WebConfig {

    @Value("\${security.encoding-strength}")
    private val encodingStrength: Int? = null


    @Bean
    fun shaPasswordEncoder(): ShaPasswordEncoder {
        return ShaPasswordEncoder(encodingStrength!!)
    }

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
