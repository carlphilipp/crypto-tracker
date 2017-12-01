package fr.cph.crypto.client

import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Ticker

interface TickerClient {

    fun getTickers(currency: Currency, vararg tickers: String): List<Ticker>

    fun getTickers(currency: Currency, tickers: List<String>): List<Ticker>

    fun getTicker(currency: Currency, ticker: String): Ticker?
}
