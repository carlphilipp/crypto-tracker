package fr.cph.crypto.config

import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.persitence.inmemory.InMemoryTickerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DbConfig {

	@Bean("inMemoryTickerRepo")
	fun tickerRepository(tickerRepository: fr.cph.crypto.persistence.mongo.repository.TickerRepository): TickerRepository {
		return InMemoryTickerAdapter()
	}
}
