package fr.cph.crypto.mongo.entity

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Role
import fr.cph.crypto.core.api.entity.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class UserDB(
        @Id var id: String? = null,
        @Indexed(unique = true) val email: String,
        var password: String,
        val role: Role,
        val currency: Currency,
        val liquidityMovement: Double,
        @DBRef
        var positions: List<PositionDB>) {

    fun toUser(): User {
        val user = User(
                email = this.email,
                password = this.password,
                role = this.role,
                currency = this.currency)
        user.id = this.id
        user.liquidityMovement = this.liquidityMovement
        user.positions = this.positions.map { position -> position.toPosition() }.toMutableList()
        return user
    }

    companion object {
        fun toDbUser(user: User): UserDB {
            return UserDB(
                    id = user.id,
                    email = user.email,
                    password = user.password,
                    role = user.role,
                    currency = user.currency,
                    liquidityMovement = user.liquidityMovement,
                    positions = user.positions.map { position -> PositionDB.toPositionDB(position) }
            )
        }
    }
}