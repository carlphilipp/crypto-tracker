/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.rest.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.cph.crypto.core.entity.Currency
import fr.cph.crypto.core.entity.User

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
		val user = User(
			email = this.email,
			password = this.password,
			currency = this.currency.toCurrency())
		user.id = this.id
		return user
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
