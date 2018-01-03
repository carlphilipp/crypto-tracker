package fr.cph.crypto.core.usecase.position

import fr.cph.crypto.core.entity.Position
import fr.cph.crypto.core.entity.User
import fr.cph.crypto.core.exception.NotAllowedException
import fr.cph.crypto.core.exception.PositionNotFoundException
import fr.cph.crypto.core.exception.UserNotFoundException
import fr.cph.crypto.core.spi.UserRepository

class UpdatePosition(private val userRepository: UserRepository) {

	fun updatePosition(userId: String, position: Position, transactionQuantity: Double?, transactionUnitCostPrice: Double?) {
		val user = userRepository.findOneUserById(userId) ?: throw UserNotFoundException(userId)
		if (transactionQuantity != null && transactionUnitCostPrice != null) {
			updatePositionSmart(user, position, transactionQuantity, transactionUnitCostPrice)
		} else {
			updatePositionManual(user, position)
		}
	}

	fun addFeeToPosition(userId: String, positionId: String, fee: Double) {
		val user = userRepository.findOneUserById(userId) ?: throw UserNotFoundException(userId)
		val position = user.positions.find { position -> position.id == positionId } ?: throw PositionNotFoundException(positionId)
		val newPosition = Position(
			id = position.id,
			currency1 = position.currency1,
			currency2 = position.currency2,
			quantity = position.quantity - fee,
			unitCostPrice = position.unitCostPrice)
		user.positions.remove(position)
		user.positions.add(newPosition)
		user.positions.sortWith(compareBy({ it.currency1.currencyName }))
		userRepository.savePosition(user, newPosition)
	}

	private fun updatePositionManual(user: User, position: Position) {
		val positionFound = user.positions.filter { it.id == position.id }.toList()
		when {
			positionFound.size == 1 -> {
				user.liquidityMovement = user.liquidityMovement + ((position.unitCostPrice * position.quantity) - (positionFound[0].unitCostPrice * positionFound[0].quantity))

				userRepository.savePosition(user, position)
			}
			positionFound.size > 1 -> throw PositionNotFoundException(position.id!!)
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
			positionFound.size > 1 -> throw PositionNotFoundException(position.id!!)
			else -> throw NotAllowedException() // TODO create position not allowed exception
		}
	}
}
