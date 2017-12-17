package fr.cph.crypto.core.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "email", "currency", "value", "originalValue", "gain", "gainPercentage", "positions")
data class User(
        val email: String,
        @JsonIgnore var password: String,
        @JsonIgnore val role: Role = Role.USER,
        val currency: Currency = Currency.USD
) {
    var id: String? = null
    // Calculated fields
    @JsonIgnore
    var liquidityMovement: Double = 0.0
    @Transient
    var value: Double? = null
    @Transient
    var originalValue: Double? = null
    @Transient
    var gain: Double? = null
    @Transient
    var gainPercentage: Double? = null

    var positions: MutableList<Position> = mutableListOf()
}

enum class Role {
    USER,
    ADMIN
}