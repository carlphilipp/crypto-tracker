package fr.cph.crypto.backend.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.cph.crypto.core.api.entity.Currency

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("code", "currencyName", "symbol", "type")
data class CurrencyDTO constructor(val code: String, val currencyName: String, val symbol: String, val type: String) {
    fun toCurrency(): Currency {
        return Currency.findCurrency(code)
    }

    companion object {
        fun from(currency: Currency): CurrencyDTO {
            return CurrencyDTO(
                    code = currency.code,
                    currencyName = currency.currencyName,
                    symbol = currency.symbol,
                    type = currency.type.toString())
        }
    }
}
