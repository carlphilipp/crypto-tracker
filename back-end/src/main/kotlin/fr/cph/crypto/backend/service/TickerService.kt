package fr.cph.crypto.backend.service

import fr.cph.crypto.backend.domain.Ticker

interface TickerService {

    fun findOne(id: String): Ticker

    fun findAll(): List<Ticker>

    fun updateAll()
}
