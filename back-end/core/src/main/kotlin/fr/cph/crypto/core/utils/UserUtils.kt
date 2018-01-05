package fr.cph.crypto.core.utils

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
			val gainPercentage = (value - originalValue) / originalValue
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
		user.gainPercentage = if (user.gain != 0.0) (totalValue - totalOriginalValue) / totalOriginalValue else 0.0
		return user
	}
}
