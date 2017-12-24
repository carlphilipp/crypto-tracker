package fr.cph.crypto.core.api.entity

data class User(var id: String? = null,
                val email: String,
                var password: String,
                val role: Role = Role.USER,
                val currency: Currency = Currency.USD,
                var liquidityMovement: Double = 0.0,
                val allowed: Boolean = false,
                var value: Double? = null,
                var originalValue: Double? = null,
                var gain: Double? = null,
                var gainPercentage: Double? = null,
                var positions: MutableList<Position> = mutableListOf())

enum class Role {
    USER,
    ADMIN
}