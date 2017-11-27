package fr.cph.crypto.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import fr.cph.crypto.client.impl.CoinMarketCapClient
import fr.cph.crypto.domain.Currency
import fr.cph.crypto.domain.Position
import fr.cph.crypto.repository.PositionRepository
import fr.cph.crypto.repository.TickerRepository
import fr.cph.crypto.service.UserService

@Service
class UserServiceImpl @Autowired
constructor(val client: CoinMarketCapClient, val positionRepository: PositionRepository, val tickerRepository: TickerRepository) : UserService {

    override fun updatePosition(position: Position): Position {

        // Update ticker in DB from client
        client.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
                .forEach { ticker -> tickerRepository.save(ticker) }
        val ticker = tickerRepository.findOne(position.currency.toString() + "-" + position.costPriceCurrency)
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
}
