package fr.cph.crypto.core.api.entity

data class User(
        val email: String,
        var password: String,
        val role: Role = Role.USER,
        val currency: Currency = Currency.USD) {

    var id: String? = null
    // Calculated fields
    var liquidityMovement: Double = 0.0
    var value: Double? = null
    var originalValue: Double? = null
    var gain: Double? = null
    var gainPercentage: Double? = null
    var positions: MutableList<Position> = mutableListOf()
}

enum class Role {
    USER,
    ADMIN
}