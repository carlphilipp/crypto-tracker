package fr.cph.crypto.backend

import fr.cph.crypto.core.Currency
import fr.cph.crypto.core.Position
import fr.cph.crypto.core.Role
import fr.cph.crypto.core.User
import fr.cph.crypto.backend.repository.PositionRepository
import fr.cph.crypto.backend.repository.TickerRepository
import fr.cph.crypto.backend.repository.UserRepository
import fr.cph.crypto.backend.service.TickerService
import fr.cph.crypto.backend.service.UserService
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
            private val positionRepository: PositionRepository,
            private val userService: UserService,
            private val tickerService: TickerService) : CommandLineRunner {

    override fun run(vararg strings: String) {
        tickerRepository.deleteAll()
        userRepository.deleteAll()
        positionRepository.deleteAll()

        tickerService.updateAll()

        val user = User("cp.harmant@gmail.com", "PASSWORD", Role.ADMIN)
        user.id = "1"
        val passwordEncoded = passwordEncoder.encodePassword(user.password, null)
        user.password = passwordEncoded
        userRepository.save(user)

        val positionBtc = Position(Currency.BTC, 0.06564277, 7616.98508457)
        userService.addPosition(user.id!!, positionBtc)
        val positionEth = Position(Currency.ETH, 1.57128061, 318.2)
        userService.addPosition(user.id!!, positionEth)
        val positionVtc = Position(Currency.VTC, 122.10096277, 4.3)
        userService.addPosition(user.id!!, positionVtc)
        val positionGrs = Position(Currency.GRS, 1025.10079425, 1.25)
        userService.addPosition(user.id!!, positionGrs)
        val positionEthos = Position(Currency.ETHOS, 189.81, 1.52)
        userService.addPosition(user.id!!, positionEthos)
        val positionCardano = Position(Currency.ADA, 3095.901, 0.12)
        userService.addPosition(user.id!!, positionCardano)
        val positionPower = Position(Currency.POWR, 443.556, 0.66)
        userService.addPosition(user.id!!, positionPower)

        val userFound = userRepository.findOne(user.id)
        userFound.liquidityMovement = 0.0
        userRepository.save(userFound)
    }
}
