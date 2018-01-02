package fr.cph.crypto.core.usecase;

import fr.cph.crypto.core.Utils;
import fr.cph.crypto.core.api.entity.Email;
import fr.cph.crypto.core.api.entity.User;
import fr.cph.crypto.core.spi.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class CreateUserTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private ShareValueRepository shareValueRepository = Mockito.mock(ShareValueRepository.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private IdGenerator idGenerator = Mockito.mock(IdGenerator.class);
	private TemplateService templateService = Mockito.mock(TemplateService.class);
	private ContextService contextService = Mockito.mock(ContextService.class);
	private EmailService emailService = Mockito.mock(EmailService.class);
	private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
	private CreateUser createUser = new CreateUser(
			userRepository = userRepository,
			idGenerator = idGenerator,
			passwordEncoder = passwordEncoder,
			templateService = templateService,
			contextService = contextService,
			emailService = emailService);

	@Test
	void testCreate() {
		// given
		User user = Utils.getUser();
		Email email = new Email("email", "Welcome to crypto tracker!", "email content");
		given(idGenerator.getNewId()).willReturn("ID");
		given(passwordEncoder.encode(user.getPassword())).willReturn("encodedPassword");
		given(passwordEncoder.encode("IDencodedPassword")).willReturn("key");
		given(userRepository.saveUser(user)).willReturn(user);
		given(contextService.getBaseUrl()).willReturn("localhost");
		given(templateService.welcomeContentEmail("localhost", "ID", "key"))
				.willReturn("email content");

		// when
		User actual = createUser.create(user);

		// then
		assertNotNull(actual);
		then(idGenerator).should().getNewId();
		then(passwordEncoder).should().encode("password");
		then(passwordEncoder).should().encode("IDencodedPassword");
		then(userRepository).should().saveUser(user);
		then(emailService).should().sendWelcomeEmail(email);
		then(contextService).should().getBaseUrl();
		then(templateService).should().welcomeContentEmail("localhost", "ID", "key");
	}
}
