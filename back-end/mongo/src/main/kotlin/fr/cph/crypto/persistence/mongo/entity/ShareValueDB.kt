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
package fr.cph.crypto.persistence.mongo.entity

import fr.cph.crypto.core.entity.ShareValue
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "share_value")
data class ShareValueDB(
	@Id var id: String? = null,
	val timestamp: Long,
	@Indexed @DBRef val user: UserDB,
	val shareQuantity: Double,
	val shareValue: Double,
	val portfolioValue: Double) {

	fun toShareValue(): ShareValue {
		val shareValue = ShareValue(
			timestamp = this.timestamp,
			user = this.user.toUser(),
			shareQuantity = this.shareQuantity,
			shareValue = this.shareValue,
			portfolioValue = this.portfolioValue
		)
		shareValue.id = this.id
		return shareValue
	}

	companion object {
		fun from(shareValue: ShareValue): ShareValueDB {
			return ShareValueDB(
				id = shareValue.id,
				timestamp = shareValue.timestamp,
				user = UserDB.from(shareValue.user),
				shareQuantity = shareValue.shareQuantity,
				shareValue = shareValue.shareValue,
				portfolioValue = shareValue.portfolioValue
			)
		}
	}
}
