package fr.cph.crypto.core.usecase.ticker

import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.core.api.exception.NotFoundException
import fr.cph.crypto.core.spi.TickerRepository

class FindTicker(private val tickerRepository: TickerRepository) {

	fun findOne(id: String): Ticker {
		return tickerRepository.findOne(id) ?: throw NotFoundException()
	}

	fun findAllById(ids: List<String>): List<Ticker> {
		return tickerRepository.findAllById(ids)
	}

	fun findAll(): List<Ticker> {
		return tickerRepository.findAll()
	}
}