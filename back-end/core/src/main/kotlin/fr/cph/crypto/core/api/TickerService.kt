package fr.cph.crypto.core.api

import fr.cph.crypto.core.api.entity.Ticker

interface TickerService {

    fun findOne(id: String): Ticker

    fun findAllById(ids: List<String>): List<Ticker>

    fun findAll(): List<Ticker>

    fun updateAll()
}