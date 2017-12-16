package fr.cph.crypto.core

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue", "lastUpdated")
@Document(collection = "position")
data class Position constructor(
        val currency1: Currency,
        val quantity: Double,
        val unitCostPrice: Double,
        val currency2: Currency = Currency.USD) {

    @Id
    var id: String? = null
    // Calculated fields
    @Transient
    var originalValue: Double? = null
    @Transient
    var value: Double? = null
    @Transient
    var gain: Double? = null
    @Transient
    var gainPercentage: Double? = null
    @Transient
    var lastUpdated: Long? = null
}

