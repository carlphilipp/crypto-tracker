package fr.cph.crypto.backend.configsec

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
    private lateinit var encodingStrength: String

    @Bean
    fun shaPasswordEncoder(): ShaPasswordEncoder {
        return ShaPasswordEncoder(encodingStrength.toInt())
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
