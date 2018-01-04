package fr.cph.crypto.core.usecase.user

import fr.cph.crypto.core.entity.Ticker
import fr.cph.crypto.core.entity.User
import fr.cph.crypto.core.exception.TickerNotFoundException

object UserUtils {
	fun enrichUser(user: User, tickers: List<Ticker>): User {
		var totalValue = 0.0
		var totalOriginalValue = 0.0
		for (position in user.positions) {
			val ticker = tickers.find { ticker -> ticker.id == position.currency1.code + "-" + position.currency2.code } ?: throw TickerNotFoundException(position.currency1.code + "-" + position.currency2.code)
			val originalValue = position.quantity * position.unitCostPrice
			val value = position.quantity * ticker.price
			val gain = value - originalValue
			val gainPercentage = (value * 100 / originalValue - 100) / 100
			position.originalValue = originalValue
			position.value = value
			position.gain = gain
			position.gainPercentage = gainPercentage
			position.lastUpdated = ticker.lastUpdated

			totalValue += value
			totalOriginalValue += originalValue
		}
		user.value = totalValue
		user.originalValue = totalOriginalValue
		user.gain = totalValue - totalOriginalValue
		user.gainPercentage = if (user.gain != 0.0) (totalValue * 100 / totalOriginalValue - 100) / 100 else 0.0
		return user
	}
}
