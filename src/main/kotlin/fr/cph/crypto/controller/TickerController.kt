package fr.cph.crypto.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Ticker
import fr.cph.crypto.repository.TickerRepository

@RequestMapping(value = "/ticker")
@RestController
class TickerController @Autowired
constructor(private val repository: TickerRepository) {

    val all: List<Ticker>
        @RequestMapping
        get() = repository.findAll().toMutableList()

    @RequestMapping(value = "/{baseCurrency}/{quoteCurrency}")
    fun getTicker(
            @PathVariable("baseCurrency") baseCurrency: Currency,
            @PathVariable("quoteCurrency") quoteCurrency: Currency): Ticker {
        return repository.findOne(baseCurrency.code + "-" + quoteCurrency.code)
    }
}
