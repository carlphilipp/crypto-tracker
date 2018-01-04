package fr.cph.crypto.core.usecase.user

import fr.cph.crypto.core.entity.User
import fr.cph.crypto.core.exception.UserNotFoundException
import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.core.spi.UserRepository
import fr.cph.crypto.core.utils.UserUtils

class FindUser(private val userRepository: UserRepository, private val tickerRepository: TickerRepository) {

	fun findOne(id: String): User {
		val user = userRepository.findOneUserById(id) ?: throw UserNotFoundException(id)
		val ids = user.positions.map { position -> position.currency1.code + "-" + position.currency2.code }.toList()
		val tickers = tickerRepository.findAllById(ids)
		return UserUtils.enrichUser(user, tickers)
	}

	fun findAll(): List<User> {
		return userRepository
			.findAllUsers()
			.map { user ->
				val ids = user.positions.map { position -> position.currency1.code + "-" + position.currency2.code }.toList()
				val tickers = tickerRepository.findAllById(ids)
				UserUtils.enrichUser(user, tickers)
			}
			.toList()
	}
}
