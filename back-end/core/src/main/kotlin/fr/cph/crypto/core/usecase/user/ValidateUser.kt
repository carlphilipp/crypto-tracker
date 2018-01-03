package fr.cph.crypto.core.usecase.user

import fr.cph.crypto.core.exception.NotFoundException
import fr.cph.crypto.core.exception.UserNotFoundException
import fr.cph.crypto.core.spi.PasswordEncoder
import fr.cph.crypto.core.spi.UserRepository

class ValidateUser(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

	fun validateUser(userId: String, key: String) {
		val user = userRepository.findOneUserById(userId) ?: throw UserNotFoundException(userId)
		if (user.allowed) throw NotFoundException(userId)
		val validKey = passwordEncoder.encode(user.id + user.password)
		if (validKey != key) throw NotFoundException(userId)
		user.allowed = true
		userRepository.saveUser(user)
	}
}
