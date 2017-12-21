package fr.cph.crypto.rest.config

import fr.cph.crypto.core.api.TickerService
import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.core.TickerServiceImpl
import fr.cph.crypto.core.core.UserServiceImpl
import fr.cph.crypto.core.spi.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfig {

    @Bean
    fun userService(userRepository: UserRepository,
                    shareValueRepository: ShareValueRepository,
                    tickerRepository: TickerRepository,
                    idGenerator: IdGenerator,
                    passwordEncoder: PasswordEncoder,
                    emailService: EmailService): UserService {
        return UserServiceImpl(
                userRepository = userRepository,
                shareValueRepository = shareValueRepository,
                tickerRepository = tickerRepository,
                idGenerator = idGenerator,
                passwordEncoder = passwordEncoder,
                emailService = emailService)
    }

    @Bean
    fun tickerService(tickerClient: TickerClient, tickerRepository: TickerRepository): TickerService {
        return TickerServiceImpl(
                client = tickerClient,
                tickerRepository = tickerRepository)
    }
}