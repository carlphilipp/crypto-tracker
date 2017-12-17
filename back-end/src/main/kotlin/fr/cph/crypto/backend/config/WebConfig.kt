package fr.cph.crypto.backend.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import fr.cph.crypto.core.api.TickerService
import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.core.TickerServiceImpl
import fr.cph.crypto.core.core.UserServiceImpl
import fr.cph.crypto.core.spi.*
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

    @Bean
    fun userService(userRepository: UserRepository,
                    shareValueRepository: ShareValueRepository,
                    positionRepository: PositionRepository,
                    tickerRepository: TickerRepository,
                    passwordEncoder: PasswordEncoder): UserService {
        return UserServiceImpl(
                userRepository = userRepository,
                shareValueRepository = shareValueRepository,
                positionRepository = positionRepository,
                tickerRepository = tickerRepository,
                passwordEncoder = passwordEncoder)
    }

    @Bean
    fun tickerService(tickerClient: TickerClient, tickerRepository: TickerRepository): TickerService {
        return TickerServiceImpl(
                client = tickerClient,
                tickerRepository = tickerRepository)
    }
}
