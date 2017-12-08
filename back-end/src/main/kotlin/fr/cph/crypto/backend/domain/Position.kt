package fr.cph.crypto.backend.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue", "lastUpdated")
@Document(collection = "position")
data class Position private constructor(
        @Id var id: String? = null,
        val currency1: Currency,
        val currency2: Currency,
        val quantity: Double,
        val unitCostPrice: Double,
        // Calculated fields
        @Transient var originalValue: Double? = null,
        @Transient var value: Double? = null,
        @Transient var gain: Double? = null,
        @Transient var gainPercentage: Double? = null,
        @Transient var lastUpdated: Long? = null
) {

    companion object {
        fun buildPosition(ticker: Ticker, quantity: Double, unitCostPrice: Double): Position {
            return Position(
                    currency1 = ticker.currency1,
                    currency2 = ticker.currency2,
                    quantity = quantity,
                    unitCostPrice = unitCostPrice)
        }
    }
}

