package fr.cph.crypto.core.usecase.position

import fr.cph.crypto.core.entity.Position
import fr.cph.crypto.core.exception.UserNotFoundException
import fr.cph.crypto.core.spi.IdGenerator
import fr.cph.crypto.core.spi.UserRepository

class AddPosition(private val userRepository: UserRepository, private val idGenerator: IdGenerator) {

	fun addPositionToUser(userId: String, position: Position) {
		val user = userRepository.findOneUserById(userId) ?: throw UserNotFoundException(userId)
		user.liquidityMovement = user.liquidityMovement + position.quantity * position.unitCostPrice
		position.id = idGenerator.getNewId()
		user.positions.add(position)
		user.positions.sortWith(compareBy({ it.currency1.currencyName }))
		userRepository.savePosition(user, position)
	}
}
