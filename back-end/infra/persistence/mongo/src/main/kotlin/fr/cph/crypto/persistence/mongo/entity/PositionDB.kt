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
package fr.cph.crypto.persistence.mongo.entity

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Position
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "position")
data class PositionDB constructor(
        @Id val id: String,
        val currency1: Currency,
        val currency2: Currency,
        val quantity: Double,
        val unitCostPrice: Double) {

    fun toPosition(): Position {
        return Position(
                id = id,
                currency1 = currency1,
                currency2 = currency2,
                quantity = quantity,
                unitCostPrice = unitCostPrice)
    }

    companion object {
        fun from(position: Position): PositionDB {
            return PositionDB(
                    id = position.id!!,
                    currency1 = position.currency1,
                    currency2 = position.currency2,
                    quantity = position.quantity,
                    unitCostPrice = position.unitCostPrice
            )
        }
    }
}

