package fr.cph.crypto.controller

import fr.cph.crypto.client.impl.CoinMarketCapClient
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Ticker
import fr.cph.crypto.repository.TickerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RefreshController @Autowired
constructor(private val repository: TickerRepository, private val client: CoinMarketCapClient) {

    @RequestMapping(value = "/refresh")
    fun refreshAll(): ResponseEntity<String> {
        client.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
                .stream()
                .forEach { ticker -> repository.save<Ticker>(ticker) }
        return ResponseEntity(HttpStatus.OK)
    }
}
