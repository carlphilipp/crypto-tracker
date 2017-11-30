package fr.cph.crypto.service

import fr.cph.crypto.domain.Ticker

interface TickerService {

    fun getOneTicker(id: String): Ticker

    fun updateAllTickers()
}
