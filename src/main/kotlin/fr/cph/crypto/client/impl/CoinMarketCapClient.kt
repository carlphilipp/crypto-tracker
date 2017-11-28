package fr.cph.crypto.client.impl

import fr.cph.crypto.client.TickerClient
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Ticker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class CoinMarketCapClient @Autowired
constructor(private val restTemplate: RestTemplate) : TickerClient {
    
    override fun getTicker(currency: Currency, ticker: String): Ticker? {
        LOGGER.debug("Search ticker: " + ticker)
        return getAllTickers(currency).firstOrNull { (curr) -> curr.code == ticker }
    }

    override fun getTickers(currency: Currency, vararg tickers: String): List<Ticker> {
        return getTickers(currency, Arrays.asList(*tickers))
    }

    override fun getTickers(currency: Currency, tickers: List<String>): List<Ticker> {
        LOGGER.debug("Search tickers: " + tickers)
        return getAllTickers(currency)
                .filter { (curr) -> tickers.contains(curr.code) }
                .toList()
    }

    private fun getAllTickers(currency: Currency): List<Ticker> {
        val uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.coinmarketcap.com")
                .path("/v1/ticker")
                .queryParam("convert", currency.toString())
                .queryParam("limit", "0")
                .build()

        val responses = restTemplate.getForObject(uriComponents.toUri(), Array<Response>::class.java)
        return responses
                .map { response -> TickerMapper.responseToTicker(response, currency) }
                .filter { (curr) -> curr != Currency.UNKNOWN }
                .toList()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CoinMarketCapClient::class.java)
    }
}
