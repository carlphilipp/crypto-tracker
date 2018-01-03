package fr.cph.crypto.core.usecase.position

import fr.cph.crypto.core.exception.NotAllowedException
import fr.cph.crypto.core.exception.UserNotFoundException
import fr.cph.crypto.core.spi.UserRepository

class DeletePosition(private val userRepository: UserRepository) {

	fun deletePosition(userId: String, positionId: String, price: Double) {
		val user = userRepository.findOneUserById(userId) ?: throw UserNotFoundException(userId)
		val positionFound = user.positions.filter { it.id == positionId }.toList()
		when {
			positionFound.size == 1 -> {
				user.liquidityMovement = user.liquidityMovement - price
				userRepository.deletePosition(user, positionFound[0])
			}
			positionFound.size > 1 -> throw RuntimeException("Something pretty bad happened")
			else -> throw NotAllowedException()
		}
	}
}
