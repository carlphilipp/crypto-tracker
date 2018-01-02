package fr.cph.crypto.core.usecase.user

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.api.exception.NotAllowedException
import fr.cph.crypto.core.api.exception.NotFoundException
import fr.cph.crypto.core.spi.UserRepository

class UpdatePosition(private val userRepository: UserRepository) {

	fun updatePosition(userId: String, position: Position, transactionQuantity: Double?, transactionUnitCostPrice: Double?) {
		val user = userRepository.findOneUserById(userId) ?: throw NotFoundException() // TODO create position not found exception
		if (transactionQuantity != null && transactionUnitCostPrice != null) {
			updatePositionSmart(user, position, transactionQuantity, transactionUnitCostPrice)
		} else {
			updatePositionManual(user, position)
		}
	}

	private fun updatePositionManual(user: User, position: Position) {
		val positionFound = user.positions.filter { it.id == position.id }.toList()
		when {
			positionFound.size == 1 -> {
				user.liquidityMovement = user.liquidityMovement + ((position.unitCostPrice * position.quantity) - (positionFound[0].unitCostPrice * positionFound[0].quantity))

				userRepository.savePosition(user, position)
			}
			positionFound.size > 1 -> throw NotFoundException()
			else -> throw NotAllowedException()
		}
	}

	private fun updatePositionSmart(user: User, position: Position, transactionQuantity: Double, transactionUnitCostPrice: Double) {
		val positionFound = user.positions.filter { it.id == position.id }.toList()
		when {
			positionFound.size == 1 -> {
				user.liquidityMovement = user.liquidityMovement + transactionUnitCostPrice * transactionQuantity

				userRepository.savePosition(user, position)
			}
			positionFound.size > 1 -> throw NotFoundException() // TODO create position not found exception
			else -> throw NotAllowedException() // TODO create position not allowed exception
		}
	}
}