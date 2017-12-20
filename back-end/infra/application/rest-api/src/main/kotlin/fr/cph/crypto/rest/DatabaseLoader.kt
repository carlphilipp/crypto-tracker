package fr.cph.crypto.rest

import fr.cph.crypto.core.api.TickerService
import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.Role
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.spi.IdGenerator
import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.core.spi.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.stereotype.Component

@Profile("dev")
@Component
class DatabaseLoader
constructor(private val tickerRepository: TickerRepository,
            private val userRepository: UserRepository,
            private val passwordEncoder: ShaPasswordEncoder,
            private val userService: UserService,
            private val idGenerator: IdGenerator,
            private val tickerService: TickerService) : CommandLineRunner {

    override fun run(vararg strings: String) {
        tickerRepository.deleteAll()
        userRepository.deleteAllUsers()
        userRepository.deleteAllPositions()

        tickerService.updateAll()

        var user = User(id = "1", email = "cp.harmant@gmail.com", password = passwordEncoder.encodePassword("PASSWORD", null), role = Role.ADMIN)
        user = userRepository.saveUser(user)

        val positionBtc = Position(id = idGenerator.getNewId(), currency1 = Currency.BTC, quantity = 0.06564277, unitCostPrice = 7616.98508457)
        userService.addPosition(user.id!!, positionBtc)
        val positionEth = Position(id = idGenerator.getNewId(), currency1 = Currency.ETH, quantity = 1.57128061, unitCostPrice = 318.2)
        userService.addPosition(user.id!!, positionEth)
        val positionVtc = Position(id = idGenerator.getNewId(), currency1 = Currency.VTC, quantity = 122.10096277, unitCostPrice = 4.3)
        userService.addPosition(user.id!!, positionVtc)
        val positionGrs = Position(id = idGenerator.getNewId(), currency1 = Currency.GRS, quantity = 1025.10079425, unitCostPrice = 1.25)
        userService.addPosition(user.id!!, positionGrs)
        val positionEthos = Position(id = idGenerator.getNewId(), currency1 = Currency.ETHOS, quantity = 189.81, unitCostPrice = 1.52)
        userService.addPosition(user.id!!, positionEthos)
        val positionCardano = Position(id = idGenerator.getNewId(), currency1 = Currency.ADA, quantity = 3095.901, unitCostPrice = 0.12)
        userService.addPosition(user.id!!, positionCardano)
        val positionPower = Position(id = idGenerator.getNewId(), currency1 = Currency.POWR, quantity = 443.556, unitCostPrice = 0.66)
        userService.addPosition(user.id!!, positionPower)

        val userFound = userRepository.findOneUserById(user.id!!)!!
        userFound.liquidityMovement = 0.0
        userRepository.saveUser(userFound)
    }
}
