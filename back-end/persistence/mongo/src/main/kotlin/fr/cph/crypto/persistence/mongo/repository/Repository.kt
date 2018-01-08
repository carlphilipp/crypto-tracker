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
package fr.cph.crypto.persistence.mongo.repository

import fr.cph.crypto.persistence.mongo.entity.PositionDB
import fr.cph.crypto.persistence.mongo.entity.ShareValueDB
import fr.cph.crypto.persistence.mongo.entity.TickerDB
import fr.cph.crypto.persistence.mongo.entity.UserDB
import org.springframework.data.mongodb.repository.MongoRepository

interface TickerRepository : MongoRepository<TickerDB, String> {
	fun findAllByOrderByMarketCapDesc(): List<TickerDB>
	fun findByIdIn(ids: List<String>): List<TickerDB>
}

interface ShareValueRepository : MongoRepository<ShareValueDB, String> {
	fun findAllByUser(user: UserDB): List<ShareValueDB>
	fun findTop1ByUserOrderByTimestampDesc(user: UserDB): ShareValueDB?
}

interface PositionRepository : MongoRepository<PositionDB, String>

interface UserRepository : MongoRepository<UserDB, String> {
	fun findOneByEmail(email: String): UserDB?
}
