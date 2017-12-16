package fr.cph.crypto.backend.controller

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Ticker
import fr.cph.crypto.backend.service.TickerService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["/api/ticker"])
@RestController
class TickerController
constructor(private val tickerService: TickerService) {

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(method = [RequestMethod.GET], produces = ["application/json"])
    fun getAllTickers(): List<Ticker> {
        return tickerService.findAll()
    }

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(value = ["/{currency1}/{currency2}"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getTicker(@PathVariable("currency1") currency1: Currency,
                  @PathVariable("currency2") currency2: Currency): Ticker {
        return tickerService.findOne(currency1.code + "-" + currency2.code)
    }
}
