package fr.cph.crypto.service

import fr.cph.crypto.domain.Ticker

interface TickerService {

    fun findOne(id: String): Ticker

    fun findAll(): List<Ticker>

    fun updateAll()
}
