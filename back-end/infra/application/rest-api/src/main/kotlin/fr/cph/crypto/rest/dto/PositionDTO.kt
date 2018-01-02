/**
 * Copyright 2017 Carl-Philipp Harmant
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

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.cph.crypto.core.entity.Currency
import fr.cph.crypto.core.entity.Position

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "quantity", "value", "gain", "gainPercentage", "unitCostPrice", "originalValue", "lastUpdated")
data class PositionDTO(var id: String? = null,
					   val currency1: CurrencyDTO,
					   val currency2: CurrencyDTO = CurrencyDTO.from(Currency.USD),
					   val quantity: Double,
					   val unitCostPrice: Double,
					   var originalValue: Double? = null,
					   var value: Double? = null,
					   var gain: Double? = null,
					   var gainPercentage: Double? = null,
					   var lastUpdated: Long? = null) {

    fun toPosition(): Position {
        val position = Position(
				currency1 = Currency.findCurrency(this.currency1.code),
				quantity = this.quantity,
				unitCostPrice = this.unitCostPrice)
        position.id = this.id
        return position
    }

    companion object {
        fun from(position: Position): PositionDTO {
            return PositionDTO(
                    id = position.id,
                    currency1 = CurrencyDTO.from(position.currency1),
                    currency2 = CurrencyDTO.from(position.currency2),
                    quantity = position.quantity,
                    unitCostPrice = position.unitCostPrice,
                    originalValue = position.originalValue,
                    value = position.value,
                    gain = position.gain,
                    gainPercentage = position.gainPercentage,
                    lastUpdated = position.lastUpdated)
        }
    }
}

