package fr.cph.crypto.persitence.inmemory

import fr.cph.crypto.core.entity.Ticker

class InMemoryTickerAdapter : fr.cph.crypto.core.spi.TickerRepository {
	private val inMemoryDb = HashMap<String, Ticker>()

	override fun findOne(id: String): Ticker? {
		return inMemoryDb.get(id)
	}

	override fun findAllById(ids: List<String>): List<Ticker> {
		return ids.map { id -> inMemoryDb[id] ?: throw RuntimeException() }
	}

	override fun findAllOrderByMarketCapDesc(): List<Ticker> {
		return inMemoryDb.values.sortedBy { ticker -> -ticker.marketCap }
	}

	override fun save(ticker: Ticker): Ticker {
		inMemoryDb.put(ticker.id!!, ticker)
		return ticker
	}

	override fun save(tickers: List<Ticker>) {
		tickers.forEach { ticker -> save(ticker) }
	}

	override fun deleteAll() {
		inMemoryDb.clear()
	}
}
