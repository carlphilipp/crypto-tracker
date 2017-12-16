package fr.cph.crypto.mongo

import fr.cph.crypto.core.Currency
import fr.cph.crypto.core.Ticker
import fr.cph.crypto.core.api.TickerClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class CoinMarketCapClient
constructor(private val restTemplate: RestTemplate) : TickerClient {

    override fun getTicker(currency: Currency, ticker: String): Ticker? {
        LOGGER.debug("Search ticker: {}", ticker)
        return getAllTickers(currency).firstOrNull { tick -> tick.currency1.code == ticker }
    }

    override fun getTickers(currency: Currency, vararg tickers: String): List<Ticker> {
        return getTickers(currency, Arrays.asList(*tickers))
    }

    override fun getTickers(currency: Currency, tickers: List<String>): List<Ticker> {
        LOGGER.debug("Search tickers: {}", tickers)
        return getAllTickers(currency)
                .filter { ticker -> tickers.contains(ticker.currency1.code) }
                .toList()
    }

    private fun getAllTickers(currency: Currency): List<Ticker> {
        val uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.coinmarketcap.com")
                .path("/v1/ticker")
                .queryParam("limit", "0")
                .queryParam("convert", currency.code)
                .build()

        LOGGER.debug("HTTP request: {}", uriComponents.toUri())
        val responses = restTemplate.getForObject(uriComponents.toUri(), Array<Response>::class.java)
        return responses
                .map { response -> TickerMapper.responseToTicker(currency, response) }
                .filter { ticker -> ticker.currency1 != Currency.UNKNOWN }
                .toList()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CoinMarketCapClient::class.java)
    }
}
