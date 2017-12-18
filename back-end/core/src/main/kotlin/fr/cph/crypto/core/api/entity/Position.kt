package fr.cph.crypto.core.api.entity

data class Position(var id: String? = null,
                    val currency1: Currency,
                    val currency2: Currency = Currency.USD,
                    val quantity: Double,
                    val unitCostPrice: Double,
                    var originalValue: Double? = null,
                    var value: Double? = null,
                    var gain: Double? = null,
                    var gainPercentage: Double? = null,
                    var lastUpdated: Long? = null)