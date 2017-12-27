package fr.cph.crypto.core

import fr.cph.crypto.core.api.entity.Currency
import fr.cph.crypto.core.api.entity.Email
import fr.cph.crypto.core.api.entity.Role
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.core.UserServiceImpl
import fr.cph.crypto.core.spi.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito

class UserServiceImplTest {

    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val shareValueRepository = Mockito.mock(ShareValueRepository::class.java)
    private val tickerRepository = Mockito.mock(TickerRepository::class.java)
    private val idGenerator = Mockito.mock(IdGenerator::class.java)
    private val templateService = Mockito.mock(TemplateService::class.java)
    private val contextService = Mockito.mock(ContextService::class.java)
    private val emailService = Mockito.mock(EmailService::class.java)
    private val passwordEncoder = Mockito.mock(PasswordEncoder::class.java)
    private val tickerService = UserServiceImpl(
            userRepository = userRepository,
            shareValueRepository = shareValueRepository,
            tickerRepository = tickerRepository,
            idGenerator = idGenerator,
            templateService = templateService,
            contextService = contextService,
            emailService = emailService,
            passwordEncoder = passwordEncoder)

    @Test
    fun testCreate() {
        // given
        val user = User(null, "email", "password", Role.USER, Currency.USD, 0.0, true)
        val email = Email("email", "Welcome to crypto tracker!", "email content")
        given(idGenerator.getNewId()).willReturn("ID")
        given(passwordEncoder.encode(user.password)).willReturn("encodedPassword")
        given(passwordEncoder.encode("IDencodedPassword")).willReturn("key")
        given(userRepository.saveUser(user)).willReturn(user)
        //given(emailService.sendWelcomeEmail(email))
        given(contextService.getBaseUrl()).willReturn("localhost")
        given(templateService.welcomeContentEmail("localhost", "ID", "key")).willReturn("email content")

        // when
        val actual = tickerService.create(user)

        // then
        assertNotNull(actual)
        then(idGenerator).should().getNewId()
        then(passwordEncoder).should().encode("password")
        then(passwordEncoder).should().encode("IDencodedPassword")
        then(userRepository).should().saveUser(user)
        then(emailService).should().sendWelcomeEmail(email)
        then(contextService).should().getBaseUrl()
        then(templateService).should().welcomeContentEmail("localhost", "ID", "key")
    }
}