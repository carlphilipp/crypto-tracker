/**
 * Copyright 2017 Carl-Philipp Harmant
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
package fr.cph.crypto.core.core

import fr.cph.crypto.core.api.TickerService
import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.core.api.exception.NotFoundException
import fr.cph.crypto.core.spi.TickerClient
import fr.cph.crypto.core.spi.TickerRepository

class TickerServiceImpl(private val client: TickerClient,
                        private val tickerRepository: TickerRepository) : TickerService {

    override fun findOne(id: String): Ticker {
        return tickerRepository.findOne(id)?: throw NotFoundException()
    }

    override fun findAllById(ids: List<String>): List<Ticker> {
        return tickerRepository.findAllById(ids)
    }

    override fun findAll(): List<Ticker> {
        return tickerRepository.findAll()
    }

    override fun updateAll() {
        client.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())
                .forEach { ticker -> tickerRepository.save(ticker) }
    }
}