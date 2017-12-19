package fr.cph.crypto.client.coinmarketcap

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.core.spi.TickerClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class CoinMarketCapAdapter(private val restTemplate: RestTemplate) : TickerClient {

    override fun getTicker(currency: Currency, ticker: String): Ticker? {
        LOGGER.debug("Search ticker: {}", ticker)
        return getAllTickers(currency).firstOrNull { tick -> tick.currency1.code == ticker }
    }

    override fun getTickers(currency: Currency, tickers: List<String>): List<Ticker> {
        LOGGER.debug("Search tickers: {}", tickers)
        return getAllTickers(currency).filter { ticker -> tickers.contains(ticker.currency1.code) }
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
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CoinMarketCapAdapter::class.java)
    }
}
