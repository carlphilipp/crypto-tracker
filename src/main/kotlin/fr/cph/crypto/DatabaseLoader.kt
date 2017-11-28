package fr.cph.crypto

import fr.cph.crypto.client.impl.CoinMarketCapClient
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.Ticker
import fr.cph.crypto.domain.User
import fr.cph.crypto.repository.PositionRepository
import fr.cph.crypto.repository.TickerRepository
import fr.cph.crypto.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class DatabaseLoader : CommandLineRunner {

    @Autowired
    private lateinit var tickerRepository: TickerRepository
    @Autowired
    private lateinit var userRepository: UserRepository
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
        val positionBtc = buildBtcPosition(btcTicker)
        val positionVtc = buildVtcPosition(vtcTicker)
        positions.add(positionBtc)
        positions.add(positionVtc)
        positionRepository.save(positionBtc)
        positionRepository.save(positionVtc)
        val user = User("cp.harmant@gmail.com")
        user.positions = positions
        userRepository.save(user)
    }

    private fun buildBtcPosition(ticker: Ticker): Position {
        val quantity = 0.06564277
        val unitCostPrice = 7616.98508457
        val originalValue = quantity * unitCostPrice
        val value = quantity * ticker.price
        val gain = value - originalValue
        val gainPercentage = value * 100 / originalValue - 100
        val position = Position(Currency.BTC, quantity, unitCostPrice, Currency.USD)
        position.originalValue = originalValue
        position.value = value
        position.gain = gain
        position.gainPercentage = gainPercentage
        return position
    }

    private fun buildVtcPosition(ticker: Ticker): Position {
        val quantity = 122.10096277
        val unitCostPrice = 4.3
        val originalValue = quantity * unitCostPrice
        val value = quantity * ticker.price
        val gain = value - originalValue
        val gainPercentage = value * 100 / originalValue - 100
        val position = Position(Currency.VTC, quantity, unitCostPrice, Currency.USD)
        position.originalValue = originalValue
        position.value = value
        position.gain = gain
        position.gainPercentage = gainPercentage
        return position
    }
}
