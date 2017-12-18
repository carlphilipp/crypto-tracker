package fr.cph.crypto.backend.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Position

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue", "lastUpdated")
data class PositionDTO constructor(
        var id: String? = null,
        val currency1: CurrencyDTO,
        val currency2: CurrencyDTO = CurrencyDTO.from(Currency.USD),
        val quantity: Double,
        val unitCostPrice: Double,
        var originalValue: Double? = null,
        var value: Double? = null,
        var gain: Double? = null,
        var gainPercentage: Double? = null,
        var lastUpdated: Long? = null) {

    fun toPosition(): Position {
        val position = Position(
                currency1 = Currency.findCurrency(this.currency1.code),
                currency2 = Currency.findCurrency(this.currency2.code),
                quantity = this.quantity,
                unitCostPrice = this.unitCostPrice
        )
        position.id = this.id
        return position
    }

    companion object {
        fun from(position: Position): PositionDTO {
            return PositionDTO(
                    id = position.id,
                    currency1 = CurrencyDTO.from(position.currency1),
                    currency2 = CurrencyDTO.from(position.currency2),
                    quantity = position.quantity,
                    unitCostPrice = position.unitCostPrice,
                    originalValue = position.originalValue,
                    value = position.value,
                    gain = position.gain,
                    gainPercentage = position.gainPercentage,
                    lastUpdated = position.lastUpdated)
        }
    }
}

