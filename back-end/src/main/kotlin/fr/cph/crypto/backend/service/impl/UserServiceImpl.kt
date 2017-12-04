package fr.cph.crypto.backend.service.impl

import fr.cph.crypto.backend.domain.Position
import fr.cph.crypto.backend.domain.User
import fr.cph.crypto.backend.repository.PositionRepository
import fr.cph.crypto.backend.repository.UserRepository
import fr.cph.crypto.backend.service.TickerService
import fr.cph.crypto.backend.service.UserService
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

    private fun enrich(user: User): User {
        // TODO optimize that code to do only one request into DB
        for (position in user.positions) {
            val ticker = tickerService.findOne(position.currency1.code + "-" + position.currency2.code)
            val originalValue = position.quantity * position.unitCostPrice
            val value = position.quantity * ticker.price
            val gain = value - originalValue
            val gainPercentage = (value * 100 / originalValue - 100) / 100
            position.originalValue = originalValue
            position.value = value
            position.gain = gain
            position.gainPercentage = gainPercentage
            position.lastUpdated = ticker.lastUpdated
        }
        return user
    }

    override fun updatePosition(position: Position): Position {
        tickerService.updateAll()
        return refreshPosition(position)
    }

    private fun refreshPosition(position: Position): Position {
        val ticker = tickerService.findOne(position.currency1.code + "-" + position.currency2.code)

        val quantity = position.quantity
        val costPrice = position.unitCostPrice
        val originalValue = quantity * costPrice
        val value = quantity * ticker.price
        val gain = value - originalValue
        val gainPercentage = (value * 100 / originalValue - 100) / 100

        position.originalValue = originalValue
        position.value = value
        position.gain = gain
        position.gainPercentage = gainPercentage

        positionRepository.save(position)
        return position
    }
}


