package fr.cph.crypto.core.core

import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.api.entity.Email
import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.api.exception.NotAllowedException
import fr.cph.crypto.core.api.exception.NotFoundException
import fr.cph.crypto.core.spi.*

class UserServiceImpl(
        private val userRepository: UserRepository,
        private val shareValueRepository: ShareValueRepository,
        private val tickerRepository: TickerRepository,
        private val idGenerator: IdGenerator,
        private val passwordEncoder: PasswordEncoder,
        private val templateService: TemplateService,
        private val contextService: ContextService,
        private val emailService: EmailService) : UserService {

    override fun create(user: User): User {
        val passwordEncoded = passwordEncoder.encode(user.password)
        user.id = idGenerator.getNewId()
        user.password = passwordEncoded
        val savedUser = userRepository.saveUser(user)
        val key = passwordEncoder.encode(user.id + passwordEncoded)
        val email = Email(savedUser.email, "Welcome to crypto tracker!", templateService.welcomeContentEmail(contextService.getBaseUrl(), savedUser.id!!, key))
        emailService.sendWelcomeEmail(email)
        return savedUser
    }

    override fun findOne(id: String): User {
        return enrich(userRepository.findOneUserById(id) ?: throw NotFoundException())
    }

    override fun findAll(): List<User> {
        return userRepository
                .findAllUsers()
                .map { user -> enrich(user) }
                .toList()
    }

    override fun addPosition(userId: String, position: Position) {
        val user = userRepository.findOneUserById(userId) ?: throw NotFoundException()
        user.liquidityMovement = user.liquidityMovement + position.quantity * position.unitCostPrice
        position.id = idGenerator.getNewId()
        user.positions.add(position)
        user.positions.sortWith(compareBy({ it.currency1.currencyName }))
        userRepository.savePosition(user, position)
    }

    override fun updatePosition(userId: String, position: Position, transactionQuantity: Double?, transactionUnitCostPrice: Double?) {
        val user = userRepository.findOneUserById(userId) ?: throw NotFoundException()
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
            positionFound.size > 1 -> throw NotFoundException()
            else -> throw NotAllowedException()
        }
    }

    override fun deletePosition(userId: String, positionId: String, price: Double) {
        val user = userRepository.findOneUserById(userId) ?: throw NotFoundException()
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

    override fun addFeeToPosition(userId: String, positionId: String, fee: Double) {
        val user = userRepository.findOneUserById(userId) ?: throw NotFoundException()
        val position = user.positions.find { position -> position.id == positionId } ?: throw NotFoundException()
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

    override fun findAllShareValue(userId: String): List<ShareValue> {
        val user = userRepository.findOneUserById(userId) ?: throw NotFoundException()
        return shareValueRepository.findAllByUser(user)
    }

    override fun updateAllUsersShareValue() {
        findAll().forEach { user ->
            run {
                addNewShareValue(user)
                user.liquidityMovement = 0.0
                userRepository.saveUser(user)
            }
        }
    }

    override fun validateUser(userId: String, key: String) {
        val user = userRepository.findOneUserById(userId) ?: throw NotFoundException()
        if(user.allowed) throw NotFoundException()
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
        user.gainPercentage = (totalValue * 100 / totalOriginalValue - 100) / 100
        return user
    }
}