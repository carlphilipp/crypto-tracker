package fr.cph.crypto.core.usecase.user

import fr.cph.crypto.core.api.entity.Email
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.spi.*

class CreateUser(private val userRepository: UserRepository,
				 private val idGenerator: IdGenerator,
				 private val passwordEncoder: PasswordEncoder,
				 private val templateService: TemplateService,
				 private val contextService: ContextService,
				 private val emailService: EmailService) {

	fun create(user: User): User {
		user.id = idGenerator.getNewId()
		user.password = passwordEncoder.encode(user.password)
		val savedUser = userRepository.saveUser(user)
		val key = passwordEncoder.encode(user.id + user.password)
		val email = Email(savedUser.email, "Welcome to crypto tracker!", templateService.welcomeContentEmail(contextService.getBaseUrl(), savedUser.id!!, key))
		emailService.sendWelcomeEmail(email)
		return savedUser
	}
}