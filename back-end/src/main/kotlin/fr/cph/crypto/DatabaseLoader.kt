package fr.cph.crypto

import fr.cph.crypto.client.impl.CoinMarketCapClient
import fr.cph.crypto.domain.*
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.repository.PositionRepository
import fr.cph.crypto.repository.TickerRepository
import fr.cph.crypto.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class DatabaseLoader : CommandLineRunner {

    @Autowired
    private lateinit var tickerRepository: TickerRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var passwordEncoder: ShaPasswordEncoder
    @Autowired
    private lateinit var positionRepository: PositionRepository
    @Autowired
    private lateinit var client: CoinMarketCapClient

    @Throws(Exception::class)
    override fun run(vararg strings: String) {
        tickerRepository.deleteAll()
        userRepository.deleteAll()
        positionRepository.deleteAll()

        client.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
                .stream()
                .forEach { ticker -> tickerRepository.save<Ticker>(ticker) }

        val btcTicker = tickerRepository.findOne("BTC-USD")
        val vtcTicker = tickerRepository.findOne("VTC-USD")

        val positions = ArrayList<Position>()
        val positionBtc = Position.buildPosition(btcTicker, 0.06564277, 7616.98508457)
        val positionVtc = Position.buildPosition(vtcTicker, 122.10096277, 4.3)
        positions.add(positionBtc)
        positions.add(positionVtc)
        positionRepository.save(positionBtc)
        positionRepository.save(positionVtc)
        val user = User("cp.harmant@gmail.com", "PASSWORD", Role.ADMIN)
        user.id = "1"
        user.positions = positions
        val passwordEncoded = passwordEncoder.encodePassword(user.password, null)
        user.password = passwordEncoded
        userRepository.save(user)
    }
}
