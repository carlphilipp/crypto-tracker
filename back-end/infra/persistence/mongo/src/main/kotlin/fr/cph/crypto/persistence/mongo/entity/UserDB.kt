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