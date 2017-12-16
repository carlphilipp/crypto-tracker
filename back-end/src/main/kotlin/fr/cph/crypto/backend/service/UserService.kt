package fr.cph.crypto.backend.service

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun create(user: User): User

    fun findOne(id: String): User

    fun findAll(): List<User>

    fun addPosition(id: String, position: Position)

    fun updatePosition(userId: String, position: Position, transactionQuantity : Double?, transactionUnitCostPrice : Double?)

    fun deletePosition(userId: String, positionId: String, price: Double)

    fun findAllShareValue(id: String): List<ShareValue>

    fun updateAllUsersShareValue()
}
