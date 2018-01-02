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
package fr.cph.crypto.core.core

import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.api.exception.NotAllowedException
import fr.cph.crypto.core.api.exception.NotFoundException
import fr.cph.crypto.core.spi.PasswordEncoder
import fr.cph.crypto.core.spi.ShareValueRepository
import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.core.spi.UserRepository

class UserServiceImpl(
		private val userRepository: UserRepository,
		private val shareValueRepository: ShareValueRepository,
		private val tickerRepository: TickerRepository,
		private val passwordEncoder: PasswordEncoder) : UserService {

	override fun updateAllUsersShareValue() {
		// FIXME see how to deal with that
/*        findAll().forEach { user ->
            run {
                addNewShareValue(user)
                user.liquidityMovement = 0.0
                userRepository.saveUser(user)
            }
        }*/
	}

	override fun validateUser(userId: String, key: String) {
		val user = userRepository.findOneUserById(userId) ?: throw NotFoundException()
		if (user.allowed) throw NotFoundException()
		val validKey = passwordEncoder.encode(user.id + user.password)
		if (validKey != key) throw NotFoundException()
		user.allowed = true
		userRepository.saveUser(user)
	}

	private fun addNewShareValue(user: User) {
		val lastShareValue = shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)
		if (lastShareValue == null) {
			addFirstTimeShareValue(user)
		} else {
			val quantity = lastShareValue.shareQuantity + (user.liquidityMovement) / ((user.value!! - user.liquidityMovement) / lastShareValue.shareQuantity)
			val shareValue = user.value!! / quantity
			val shareValueToSave = ShareValue(
					timestamp = System.currentTimeMillis(),
					user = user,
					shareQuantity = quantity,
					shareValue = shareValue,
					portfolioValue = user.value!!)
			shareValueRepository.save(shareValueToSave)
		}
	}

	private fun addFirstTimeShareValue(user: User) {
		val yesterdayShareValue = ShareValue(
				timestamp = System.currentTimeMillis() - 86400000,
				user = user,
				shareQuantity = user.originalValue!! / 100,
				shareValue = 100.0,
				portfolioValue = user.originalValue!!)
		shareValueRepository.save(yesterdayShareValue)
		val defaultShareValue = user.gainPercentage!! * 100 + 100
		val shareValueToSave = ShareValue(
				timestamp = System.currentTimeMillis(),
				user = user,
				shareQuantity = user.value!! / defaultShareValue,
				shareValue = defaultShareValue,
				portfolioValue = user.value!!)
		shareValueRepository.save(shareValueToSave)
	}

	private fun enrich(user: User): User {
		val ids = user.positions.map { position -> position.currency1.code + "-" + position.currency2.code }.toList()
		val tickers = tickerRepository.findAllById(ids)
		var totalValue = 0.0
		var totalOriginalValue = 0.0
		for (position in user.positions) {
			val ticker = tickers.find { ticker -> ticker.id == position.currency1.code + "-" + position.currency2.code } ?: throw NotFoundException()
			val originalValue = position.quantity * position.unitCostPrice
			val value = position.quantity * ticker.price
			val gain = value - originalValue
			val gainPercentage = (value * 100 / originalValue - 100) / 100
			position.originalValue = originalValue
			position.value = value
			position.gain = gain
			position.gainPercentage = gainPercentage
			position.lastUpdated = ticker.lastUpdated

			totalValue += value
			totalOriginalValue += originalValue
		}
		user.value = totalValue
		user.originalValue = totalOriginalValue
		user.gain = totalValue - totalOriginalValue
		user.gainPercentage = if (user.gain != 0.0) (totalValue * 100 / totalOriginalValue - 100) / 100 else 0.0
		return user
	}
}