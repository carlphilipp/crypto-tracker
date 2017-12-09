package fr.cph.crypto.backend

import fr.cph.crypto.backend.domain.Position
import fr.cph.crypto.backend.domain.Role
import fr.cph.crypto.backend.domain.User
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
constructor(
        private val tickerRepository: TickerRepository,
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

        val btcTicker = tickerRepository.findOne("BTC-USD")
        val ethTicker = tickerRepository.findOne("ETH-USD")
        val vtcTicker = tickerRepository.findOne("VTC-USD")
        val grsTicker = tickerRepository.findOne("GRS-USD")
        val ethosTicker = tickerRepository.findOne("ETHOS-USD")
        val cardanoTicker = tickerRepository.findOne("ADA-USD")
        val powerTicker = tickerRepository.findOne("POWR-USD")

        val user = User(email = "cp.harmant@gmail.com", password = "PASSWORD", role = Role.ADMIN)
        user.id = "1"
        val passwordEncoded = passwordEncoder.encodePassword(user.password, null)
        user.password = passwordEncoded
        userRepository.save(user)

        val positionBtc = Position.buildPosition(btcTicker, 0.06564277, 7616.98508457)
        userService.addPosition(user.id!!, positionBtc)
        val positionEth = Position.buildPosition(ethTicker, 1.57128061, 318.2)
        userService.addPosition(user.id!!, positionEth)
        val positionVtc = Position.buildPosition(vtcTicker, 122.10096277, 4.3)
        userService.addPosition(user.id!!, positionVtc)
        val positionGrs = Position.buildPosition(grsTicker, 1025.10079425, 1.25)
        userService.addPosition(user.id!!, positionGrs)
        val positionEthos = Position.buildPosition(ethosTicker, 189.81, 1.52)
        userService.addPosition(user.id!!, positionEthos)
        val positionCardano = Position.buildPosition(cardanoTicker, 3095.901, 0.12)
        userService.addPosition(user.id!!, positionCardano)
        val positionPower = Position.buildPosition(powerTicker, 443.556, 0.66)
        userService.addPosition(user.id!!, positionPower)

    }
}
