package fr.cph.crypto.core.api.entity

data class Position constructor(
        val currency1: Currency,
        val quantity: Double,
        val unitCostPrice: Double,
        val currency2: Currency = Currency.USD) {

    var id: String? = null
    // Calculated fields
    var originalValue: Double? = null
    var value: Double? = null
    var gain: Double? = null
    var gainPercentage: Double? = null
    var lastUpdated: Long? = null
}

