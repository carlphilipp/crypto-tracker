package fr.cph.crypto.core.usecase.user

import fr.cph.crypto.core.entity.User
import fr.cph.crypto.core.exception.NotAllowedException
import fr.cph.crypto.core.spi.UserRepository

class LoginUser(private val userRepository: UserRepository) {

	fun login(username: String): User {
		val user = userRepository.findOneUserByEmail(username) ?: throw NotAllowedException()
		if (!user.allowed) throw NotAllowedException()
		return user
	}
}
