package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.Ticker

interface TickerRepository {

    fun findOne(id: String): Ticker?

    fun findAllById(ids: List<String>): List<Ticker>

    fun findAll(): List<Ticker>

    fun save(ticker: Ticker): Ticker

    fun deleteAll()
}