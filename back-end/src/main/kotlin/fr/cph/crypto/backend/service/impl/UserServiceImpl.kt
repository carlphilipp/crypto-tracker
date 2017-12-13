package fr.cph.crypto.backend.service.impl

import fr.cph.crypto.backend.domain.Position
import fr.cph.crypto.backend.domain.ShareValue
import fr.cph.crypto.backend.domain.User
import fr.cph.crypto.backend.exception.NotAllowedException
import fr.cph.crypto.backend.repository.PositionRepository
import fr.cph.crypto.backend.repository.UserRepository
import fr.cph.crypto.backend.service.ShareValueService
import fr.cph.crypto.backend.service.TickerService
import fr.cph.crypto.backend.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserServiceImpl
constructor(private val positionRepository: PositionRepository,
            private val userRepository: UserRepository,
            private val tickerService: TickerService,
            private val shareValueService: ShareValueService,
            private val passwordEncoder: ShaPasswordEncoder) : UserService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findOneByEmail(username)
        val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority(user!!.role.name))
        return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
    }

    override fun create(user: User): User {
        val passwordEncoded = passwordEncoder.encodePassword(user.password, null)
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

    override fun updateAllUsersShareValue() {
        findAll().forEach { user ->
            run {
                shareValueService.addNewShareValue(user)
                user.liquidityMovement = 0.0
                userRepository.save(user)
            }
        }
    }

    private fun enrich(user: User): User {
        val ids = user.positions.map { position -> position.currency1.code + "-" + position.currency2.code }.toList()
        val tickers = tickerService.findAllById(ids)
        var totalValue = 0.0
        var totalOriginalValue = 0.0
        for (position in user.positions) {
            val ticker = tickers.find { ticker -> ticker.id == position.currency1.code + "-" + position.currency2.code }!!
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

    override fun addPosition(id: String, position: Position) {
        positionRepository.save(position)

        val user = userRepository.findOne(id)
        user.positions.add(position)
        user.positions.sortWith(compareBy({ it.currency1.currencyName }))
        user.liquidityMovement = user.liquidityMovement + position.quantity * position.unitCostPrice

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
        val positionFound = user.positions.filter { it.id == position.id }.toList()
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
        val positionFound = user.positions.filter { it.id == position.id }.toList()
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
        val positionFound = user.positions.filter { it.id == positionId }.toList()
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
        val user = userRepository.findOne(id)
        return shareValueService.findAllShareValue(user)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }
}


