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
        @DBRef
        var positions: MutableList<PositionDB>) {

    fun toUser(): User {
        val user = User(
                id = this.id,
                email = this.email,
                password = this.password,
                role = this.role,
                currency = this.currency)
        user.liquidityMovement = this.liquidityMovement
        user.positions = this.positions.map { position -> position.toPosition() }.toMutableList()
        return user
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
                    positions = user.positions.map { position -> PositionDB.from(position) }.toMutableList()
            )
        }
    }
}