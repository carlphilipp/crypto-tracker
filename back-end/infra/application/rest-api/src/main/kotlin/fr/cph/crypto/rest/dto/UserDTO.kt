package fr.cph.crypto.rest.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.User

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "email", "currency", "value", "originalValue", "gain", "gainPercentage", "positions")
data class UserDTO(var id: String? = null,
                   val email: String,
                   @JsonIgnore val password: String,
                   val currency: CurrencyDTO = CurrencyDTO.from(Currency.USD),
                   var value: Double? = null,
                   var originalValue: Double? = null,
                   var gain: Double? = null,
                   var gainPercentage: Double? = null,
                   var positions: List<PositionDTO> = listOf()) {

    fun toUser(): User {
        return User(
                id = this.id,
                email = this.email,
                password = this.password,
                currency = this.currency.toCurrency())
    }

    companion object {
        fun from(user: User): UserDTO {
            return UserDTO(
                    id = user.id,
                    email = user.email,
                    password = user.password,
                    value = user.value,
                    originalValue = user.originalValue,
                    gain = user.gain,
                    gainPercentage = user.gainPercentage,
                    positions = user.positions.map { position -> PositionDTO.from(position) },
                    currency = CurrencyDTO.from(user.currency))
        }
    }
}