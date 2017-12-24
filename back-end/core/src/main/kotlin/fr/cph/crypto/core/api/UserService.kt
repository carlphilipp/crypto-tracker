package fr.cph.crypto.core.api

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User

interface UserService {

    fun create(user: User): User

    fun findOne(id: String): User

    fun findAll(): List<User>

    fun addPosition(userId: String, position: Position)

    fun updatePosition(userId: String, position: Position, transactionQuantity: Double?, transactionUnitCostPrice: Double?)

    fun deletePosition(userId: String, positionId: String, price: Double)

    fun addFeeToPosition(userId: String, positionId: String, fee: Double)

    fun findAllShareValue(userId: String): List<ShareValue>

    fun updateAllUsersShareValue()

    fun validateUser(userId: String, key: String)
}