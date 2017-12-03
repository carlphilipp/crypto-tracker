package fr.cph.crypto.backend.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue", "lastUpdated")
@Document
data class Position private constructor(
        val currency1: Currency,
        val currency2: Currency,
        val quantity: Double,
        val unitCostPrice: Double,
        val lastUpdated: Long) {

    @Id
    var id: String? = null

    // Calculated fields
    var originalValue: Double? = null
    var value: Double? = null
    var gain: Double? = null
    var gainPercentage: Double? = null

    companion object {
        fun buildPosition(ticker: Ticker, quantity: Double, unitCostPrice: Double): Position {
            val originalValue = quantity * unitCostPrice
            val value = quantity * ticker.price
            val gain = value - originalValue
            val gainPercentage = value * 100 / originalValue - 100
            val position = Position(ticker.currency1, ticker.currency2, quantity, unitCostPrice, ticker.lastUpdated)
            position.originalValue = originalValue
            position.value = value
            position.gain = gain
            position.gainPercentage = gainPercentage
            return position
        }
    }
}
