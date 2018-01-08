/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.persistence.mongo

import fr.cph.crypto.core.entity.Ticker
import fr.cph.crypto.persistence.mongo.entity.TickerDB
import fr.cph.crypto.persistence.mongo.repository.TickerRepository

class MongoTickerAdapter(private val repository: TickerRepository) : fr.cph.crypto.core.spi.TickerRepository {

	override fun findOne(id: String): Ticker? {
		return repository.findOne(id)?.toTicker()
	}

	override fun findAllById(ids: List<String>): List<Ticker> {
		return repository.findByIdIn(ids).map { ticker -> ticker.toTicker() }
	}

	override fun findAllOrderByMarketCapDesc(): List<Ticker> {
		return repository.findAllByOrderByMarketCapDesc()
			.map { ticker -> ticker.toTicker() }
	}

	override fun save(ticker: Ticker): Ticker {
		return repository.save(TickerDB.from(ticker)).toTicker()
	}

	override fun save(tickers: List<Ticker>) {
		repository.save(tickers.map { ticker -> TickerDB.from(ticker) })
	}

	override fun deleteAll() {
		return repository.deleteAll()
	}
}
