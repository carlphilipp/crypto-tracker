package fr.cph.crypto.backend.controller

import fr.cph.crypto.backend.domain.Currency
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyController {

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(value = ["/api/currency"], method = [RequestMethod.GET], produces = ["application/json"])
    fun findAllCryptoCurrencies(): Map<String, Currency> {
        return Currency.cryptoCurrenciesAsList().map { it.code to it }.toMap()
    }
}
