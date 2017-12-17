package fr.cph.crypto.core.api.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue", "lastUpdated")
data class Position constructor(
        val currency1: Currency,
        val quantity: Double,
        val unitCostPrice: Double,
        val currency2: Currency = Currency.USD) {

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

