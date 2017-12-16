package fr.cph.crypto.core.api

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Ticker

interface TickerClient {

    fun getTickers(currency: Currency, vararg tickers: String): List<Ticker>

    fun getTickers(currency: Currency, tickers: List<String>): List<Ticker>

    fun getTicker(currency: Currency, ticker: String): Ticker?
}
