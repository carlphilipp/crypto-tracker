package fr.cph.crypto.service.impl

import fr.cph.crypto.client.impl.CoinMarketCapClient
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.Ticker
import fr.cph.crypto.domain.User
import fr.cph.crypto.repository.PositionRepository
import fr.cph.crypto.repository.TickerRepository
import fr.cph.crypto.repository.UserRepository
import fr.cph.crypto.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired
constructor(private val client: CoinMarketCapClient,
            private val positionRepository: PositionRepository,
            private val tickerRepository: TickerRepository,
            private val userRepository: UserRepository) : UserService {

    override fun refreshUser(id: String): User {
        val user = userRepository.findOne(id)
        updateAllTickers()
        user.positions.forEach { position -> refreshPosition(position) }
        return userRepository.findOne(id)
    }

    override fun refreshPosition(position: Position): Position {

        val ticker = tickerRepository.findOne(position.currency1.name + "-" + position.currency2.name)
        ////////////////////////////////////

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

    override fun updatePosition(position: Position): Position {
        // Update ticker in DB from client
        updateAllTickers()
        return refreshPosition(position)
    }

    private fun updateAllTickers() {
        client.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
                .forEach { ticker -> tickerRepository.save(ticker) }
    }
}
