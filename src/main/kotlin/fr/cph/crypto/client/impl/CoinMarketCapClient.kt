package fr.cph.crypto.client.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import java.util.Arrays
import java.util.Optional

import fr.cph.crypto.client.TickerClient
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Ticker

@Component
class CoinMarketCapClient : TickerClient {

    @Autowired
    private val restTemplate: RestTemplate? = null

    override fun getTicker(currency: Currency, ticker: String): Optional<Ticker> {
        return getAllTickers(currency).stream()
                .filter { (currency1) -> currency1.code == ticker }
                .findAny()
    }

    override fun getTickers(currency: Currency, vararg tickers: String): List<Ticker> {
        return getTickers(currency, Arrays.asList(*tickers))
    }

    override fun getTickers(currency: Currency, tickers: List<String>): List<Ticker> {
        return getAllTickers(currency)
                .filter { (currency1) -> tickers.contains(currency1.code) }
                .toMutableList()
    }

    private fun getAllTickers(currency: Currency): List<Ticker> {
        val uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.coinmarketcap.com")
                .path("/v1/ticker")
                .queryParam("convert", currency.toString())
                .queryParam("limit", "0")
                .build()

        val responses = restTemplate!!.getForObject(uriComponents.toUri(), Array<Response>::class.java)
        return responses
                .map { response -> TickerMapper.responseToTicker(response, currency) }
                .filter { (currency1) -> currency1 != Currency.UNKNOWN }
                .toMutableList()
    }
}
