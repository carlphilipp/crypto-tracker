package fr.cph.crypto.rest.config

import fr.cph.crypto.core.spi.IdGenerator
import fr.cph.crypto.uuid.mongo.MongoIdGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InfraConfig {

    @Bean
    fun uuidGenerator(): IdGenerator {
        return MongoIdGenerator()
    }
}