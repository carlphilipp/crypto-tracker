package fr.cph.crypto.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import fr.cph.crypto.client.impl.CoinMarketCapClient
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Ticker
import fr.cph.crypto.repository.TickerRepository

@RestController
class RefreshController {

    @Autowired
    private val repository: TickerRepository? = null
    @Autowired
    private val client: CoinMarketCapClient? = null

    @RequestMapping(value = "/refresh")
    fun refreshAll(): ResponseEntity<String> {
        client!!.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
                .stream()
                .forEach { ticker -> repository!!.save<Ticker>(ticker) }
        return ResponseEntity(HttpStatus.OK)
    }
}
