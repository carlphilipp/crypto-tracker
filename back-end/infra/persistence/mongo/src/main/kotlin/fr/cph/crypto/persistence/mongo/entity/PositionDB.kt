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

