package fr.cph.crypto.core.core

import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.api.exception.NotAllowedException
import fr.cph.crypto.core.spi.PasswordEncoder
import fr.cph.crypto.core.spi.PositionRepository
import fr.cph.crypto.core.spi.ShareValueRepository
import fr.cph.crypto.core.spi.UserRepository

class UserServiceImpl(
        private val userRepository: UserRepository,
        private val shareValueRepository: ShareValueRepository,
        private val positionRepository: PositionRepository,
        private val passwordEncoder: PasswordEncoder) : UserService {

    override fun create(user: User): User {
        val passwordEncoded = passwordEncoder.encodePassword(user.password)
        user.password = passwordEncoded
        return userRepository.save(user)
    }

    override fun findOne(id: String): User {
        return enrich(userRepository.findOne(id))
    }

    override fun findAll(): List<User> {
        return userRepository
                .findAll()
                .map { user -> enrich(user) }
                .toList()
    }

    override fun addPosition(id: String, position: Position) {
        val savedPosition = positionRepository.save(position)


        val user = userRepository.findOne(id)
        user!!.positions.add(savedPosition)
        user.positions.sortWith(compareBy({ it.currency1.currencyName }))
        user.liquidityMovement = user.liquidityMovement + savedPosition.quantity * savedPosition.unitCostPrice

        userRepository.save(user)
    }

    override fun updatePosition(userId: String, position: Position, transactionQuantity: Double?, transactionUnitCostPrice: Double?) {
        if (transactionQuantity != null && transactionUnitCostPrice != null) {
            updatePositionSmart(userId, position, transactionQuantity, transactionUnitCostPrice)
        } else {
            updatePositionManual(userId, position)
        }
    }

    private fun updatePositionManual(userId: String, position: Position) {
        val user = userRepository.findOne(userId)
        val positionFound = user!!.positions.filter { it.id == position.id }.toList()
        when {
            positionFound.size == 1 -> {
                user.liquidityMovement = user.liquidityMovement + ((position.unitCostPrice * position.quantity) - (positionFound[0].unitCostPrice * positionFound[0].quantity))

                positionRepository.save(position)

                userRepository.save(user)
            }
            positionFound.size > 1 -> throw RuntimeException("Something pretty bad happened")
            else -> throw NotAllowedException()
        }
    }

    private fun updatePositionSmart(userId: String, position: Position, transactionQuantity: Double, transactionUnitCostPrice: Double) {
        val user = userRepository.findOne(userId)
        val positionFound = user!!.positions.filter { it.id == position.id }.toList()
        when {
            positionFound.size == 1 -> {
                user.liquidityMovement = user.liquidityMovement + transactionUnitCostPrice * transactionQuantity

                positionRepository.save(position)

                userRepository.save(user)
            }
            positionFound.size > 1 -> throw RuntimeException("Something pretty bad happened")
            else -> throw NotAllowedException()
        }
    }

    override fun deletePosition(userId: String, positionId: String, price: Double) {
        val user = userRepository.findOne(userId)
        val positionFound = user!!.positions.filter { it.id == positionId }.toList()
        when {
            positionFound.size == 1 -> {
                user.liquidityMovement = user.liquidityMovement - price

                user.positions.remove(positionFound[0])
                positionRepository.delete(positionId)
                userRepository.save(user)
            }
            positionFound.size > 1 -> throw RuntimeException("Something pretty bad happened")
            else -> throw NotAllowedException()
        }
    }

    override fun findAllShareValue(id: String): List<ShareValue> {
        val user = userRepository.findOne(id)!!
        return shareValueRepository.findAllByUser(user)
    }

    override fun updateAllUsersShareValue() {
        findAll().forEach { user ->
            run {
                addNewShareValue(user)
                user.liquidityMovement = 0.0
                userRepository.save(user)
            }
        }
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

    private fun enrich(user: User?): User {
        // TODO
        return user!!
    }

}