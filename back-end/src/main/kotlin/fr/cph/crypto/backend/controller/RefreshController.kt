package fr.cph.crypto.backend.controller

import fr.cph.crypto.backend.client.impl.CoinMarketCapClient
import fr.cph.crypto.backend.domain.Currency
import fr.cph.crypto.backend.domain.Ticker
import fr.cph.crypto.backend.repository.TickerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class RefreshController
constructor(private val tickerRepository: TickerRepository, private val client: CoinMarketCapClient) {

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(value = ["/api/refresh"], method = [RequestMethod.GET], produces = ["application/json"])
    fun refreshAll(): ResponseEntity<String> {
        client.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())
                .forEach { ticker -> tickerRepository.save<Ticker>(ticker) }
        return ResponseEntity("{}", HttpStatus.OK)
    }
}
