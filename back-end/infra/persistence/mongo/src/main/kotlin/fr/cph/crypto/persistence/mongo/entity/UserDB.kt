package fr.cph.crypto.persistence.mongo.entity

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Role
import fr.cph.crypto.core.api.entity.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class UserDB(
        @Id val id: String,
        @Indexed(unique = true) val email: String,
        var password: String,
        val role: Role,
        val currency: Currency,
        val liquidityMovement: Double,
        val allowed: Boolean = false,
        @DBRef
        var positions: List<PositionDB>) {

    fun toUser(): User {
        return User(
                id = this.id,
                email = this.email,
                password = this.password,
                role = this.role,
                currency = this.currency,
                liquidityMovement = this.liquidityMovement,
                allowed = this.allowed,
                positions = this.positions.map { position -> position.toPosition() }.toMutableList())
    }

    companion object {
        fun from(user: User): UserDB {
            return UserDB(
                    id = user.id!!,
                    email = user.email,
                    password = user.password,
                    role = user.role,
                    currency = user.currency,
                    liquidityMovement = user.liquidityMovement,
                    allowed = user.allowed,
                    positions = user.positions.map { position -> PositionDB.from(position) }.toList()
            )
        }
    }
}