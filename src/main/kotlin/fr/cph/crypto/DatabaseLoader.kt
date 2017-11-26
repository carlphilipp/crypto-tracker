package fr.cph.crypto

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
    private val tickerRepository: TickerRepository? = null
    @Autowired
    private val userRepository: UserRepository? = null
    @Autowired
    private val positionRepository: PositionRepository? = null

    @Throws(Exception::class)
    override fun run(vararg strings: String) {
        tickerRepository!!.deleteAll()
        userRepository!!.deleteAll()
        positionRepository!!.deleteAll()
        val ticker = Ticker(Currency.BTC, 6000.0, 0.0, 0.0, 0.0, "")
        ticker.id = "BTC-USD"

        tickerRepository!!.save(ticker)

        val positions = ArrayList<Position>()

        val quantity = 2.0
        val costPrice = 5000.0
        val originalValue = quantity * costPrice
        val value = quantity * ticker.price
        val gain = value - originalValue
        val gainPercentage = value * 100 / originalValue - 100
        val position = Position(Currency.BTC, quantity, 5000.0, Currency.USD)
        position.originalValue = originalValue
        position.value = value
        position.gain = gain
        position.gainPercentage = gainPercentage
        positions.add(position)
        positionRepository!!.save(position)
        val user = User("cp.harmant@gmail.com")
        user.positions = positions
        userRepository!!.save(user)
    }
}
