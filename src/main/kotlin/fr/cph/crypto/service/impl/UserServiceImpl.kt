package fr.cph.crypto.service.impl

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User
import fr.cph.crypto.repository.PositionRepository
import fr.cph.crypto.repository.UserRepository
import fr.cph.crypto.service.TickerService
import fr.cph.crypto.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service
class UserServiceImpl @Autowired
constructor(private val positionRepository: PositionRepository,
            private val userRepository: UserRepository,
            private val tickerService: TickerService,
            private val passwordEncoder: ShaPasswordEncoder) : UserService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findOneByEmail(username)
        val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority(user.role.name))
        return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
    }

    override fun createUser(user: User): User {
        val passwordEncoded = passwordEncoder.encodePassword(user.password, null)
        user.password = passwordEncoded
        return userRepository.save(user)
    }

    override fun getOneUser(id: String): User {
        return userRepository.findOne(id)
    }

    override fun getAllUsers(): List<User> {
        return userRepository.findAll().toList()
    }

    override fun refreshUserPositions(id: String): List<Position> {
        val user = userRepository.findOne(id)
        tickerService.updateAllTickers()
        user.positions.forEach { position -> refreshPosition(position) }
        return userRepository.findOne(id).positions
    }

    override fun updatePosition(position: Position): Position {
        tickerService.updateAllTickers()
        return refreshPosition(position)
    }

    private fun refreshPosition(position: Position): Position {
        val ticker = tickerService.getOneTicker(position.currency1.code + "-" + position.currency2.code)

        val quantity = position.quantity
        val costPrice = position.unitCostPrice
        val originalValue = quantity * costPrice
        val value = quantity * ticker.price
        val gain = value - originalValue
        val gainPercentage = value * 100 / originalValue - 100

        position.originalValue = originalValue
        position.value = value
        position.gain = gain
        position.gainPercentage = gainPercentage

        positionRepository.save(position)
        return position
    }
}


