/**
 * Copyright 2018 Carl-Philipp Harmant
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
package fr.cph.crypto.persistence.mongo

import fr.cph.crypto.core.entity.Position
import fr.cph.crypto.core.entity.User
import fr.cph.crypto.persistence.mongo.entity.PositionDB
import fr.cph.crypto.persistence.mongo.entity.UserDB
import fr.cph.crypto.persistence.mongo.repository.PositionRepository
import fr.cph.crypto.persistence.mongo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class MongoUserAdapter(private val userRepository: UserRepository,
					   private val positionRepository: PositionRepository) : fr.cph.crypto.core.spi.UserRepository {

	override fun findOneUserByEmail(email: String): User? {
		return userRepository.findOneByEmail(email)?.toUser()
	}

	override fun findOneUserById(id: String): User? {
		return userRepository.findOne(id)?.toUser()
	}

	override fun findAllUsers(): List<User> {
		return userRepository.findAll().map { user -> user.toUser() }
	}

	override fun saveUser(user: User): User {
		return userRepository.save(UserDB.from(user)).toUser()
	}

	override fun savePosition(user: User, position: Position): Position {
		val savedPosition = positionRepository.save(PositionDB.from(position)).toPosition()
		saveUser(user)
		return savedPosition
	}

	override fun deletePosition(user: User, position: Position) {
		user.positions.remove(position)
		positionRepository.delete(position.id)
		userRepository.save(UserDB.from(user))
	}

	override fun deleteAllPositions() {
		positionRepository.deleteAll()
	}

	override fun deleteAllUsers() {
		userRepository.deleteAll()
	}
}
