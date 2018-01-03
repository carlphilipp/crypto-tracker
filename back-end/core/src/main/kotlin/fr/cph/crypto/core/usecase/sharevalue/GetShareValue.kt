package fr.cph.crypto.core.usecase.sharevalue

import fr.cph.crypto.core.entity.ShareValue
import fr.cph.crypto.core.exception.ShareValueNotFoundException
import fr.cph.crypto.core.spi.ShareValueRepository
import fr.cph.crypto.core.spi.UserRepository

class GetShareValue(private val userRepository: UserRepository, private val shareValueRepository: ShareValueRepository) {

	fun findAllShareValue(userId: String): List<ShareValue> {
		val user = userRepository.findOneUserById(userId) ?: throw ShareValueNotFoundException(userId)
		return shareValueRepository.findAllByUser(user)
	}
}
