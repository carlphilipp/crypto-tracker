package fr.cph.crypto.core.usecase.user

import fr.cph.crypto.core.entity.User
import fr.cph.crypto.core.exception.TickerNotFoundException
import fr.cph.crypto.core.exception.UserNotFoundException
import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.core.spi.UserRepository

class FindUser(private val userRepository: UserRepository, private val tickerRepository: TickerRepository) {

	fun findOne(id: String): User {
		return enrich(userRepository.findOneUserById(id) ?: throw UserNotFoundException(id))
	}

	fun findAll(): List<User> {
		return userRepository
			.findAllUsers()
			.map { user -> enrich(user) }
			.toList()
	}

	private fun enrich(user: User): User {
		val ids = user.positions.map { position -> position.currency1.code + "-" + position.currency2.code }.toList()
		val tickers = tickerRepository.findAllById(ids)
		var totalValue = 0.0
		var totalOriginalValue = 0.0
		for (position in user.positions) {
			val ticker = tickers.find { ticker -> ticker.id == position.currency1.code + "-" + position.currency2.code } ?: throw TickerNotFoundException(position.currency1.code + "-" + position.currency2.code)
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
