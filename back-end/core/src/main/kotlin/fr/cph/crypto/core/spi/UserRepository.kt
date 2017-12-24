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
package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.User

interface UserRepository {

    fun findOneUserByEmail(email: String): User?

    fun findOneUserById(id: String): User?

    fun findAllUsers(): List<User>

    fun saveUser(user: User): User

    fun deleteAllUsers()

    fun savePosition(user: User, position: Position): Position

    fun deletePosition(user: User, position: Position)

    fun deleteAllPositions()
}