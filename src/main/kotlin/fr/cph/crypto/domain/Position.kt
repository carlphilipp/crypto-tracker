package fr.cph.crypto.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue")
@Document
data class Position(val currency1: Currency,
                    val currency2: Currency,
                    val quantity: Double,
                    val unitCostPrice: Double
) {

    @Id
    var id: String? = null

    // Calculated fields
    var originalValue: Double? = null
    var value: Double? = null
    var gain: Double? = null
    var gainPercentage: Double? = null
}
