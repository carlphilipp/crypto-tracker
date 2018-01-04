package fr.cph.crypto.core.usecase.sharevalue

import fr.cph.crypto.core.entity.ShareValue
import fr.cph.crypto.core.entity.User
import fr.cph.crypto.core.spi.ShareValueRepository
import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.core.spi.UserRepository
import fr.cph.crypto.core.usecase.user.UserUtils

class UpdateShareValue(private val shareValueRepository: ShareValueRepository,
					   private val userRepository: UserRepository,
					   private val tickerRepository: TickerRepository) {

	fun updateAllUsersShareValue() {
		userRepository.findAllUsers().forEach { user ->
			run {
				val ids = user.positions.map { position -> position.currency1.code + "-" + position.currency2.code }.toList()
				val tickers = tickerRepository.findAllById(ids)
				addNewShareValue(UserUtils.enrichUser(user, tickers))
				user.liquidityMovement = 0.0
				userRepository.saveUser(user)
			}
		}
	}

	private fun addNewShareValue(user: User) {
		val lastShareValue = shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)
		if (lastShareValue == null) {
			addFirstTimeShareValue(user)
		} else {
			val quantity = lastShareValue.shareQuantity + (user.liquidityMovement) / ((user.value!! - user.liquidityMovement) / lastShareValue.shareQuantity)
			val shareValue = user.value!! / quantity
			val shareValueToSave = ShareValue(
					timestamp = System.currentTimeMillis(),
					user = user,
					shareQuantity = quantity,
					shareValue = shareValue,
					portfolioValue = user.value!!)
			shareValueRepository.save(shareValueToSave)
		}
	}

	private fun addFirstTimeShareValue(user: User) {
		val yesterdayShareValue = ShareValue(
				timestamp = System.currentTimeMillis() - 86400000,
				user = user,
				shareQuantity = user.originalValue!! / 100,
				shareValue = 100.0,
				portfolioValue = user.originalValue!!)
		shareValueRepository.save(yesterdayShareValue)
		val defaultShareValue = user.gainPercentage!! * 100 + 100
		val shareValueToSave = ShareValue(
				timestamp = System.currentTimeMillis(),
				user = user,
				shareQuantity = user.value!! / defaultShareValue,
				shareValue = defaultShareValue,
				portfolioValue = user.value!!)
		shareValueRepository.save(shareValueToSave)
	}
}
