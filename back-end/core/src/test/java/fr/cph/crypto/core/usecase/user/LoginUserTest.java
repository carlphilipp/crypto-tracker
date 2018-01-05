package fr.cph.crypto.core.usecase.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import fr.cph.crypto.core.Utils;
import fr.cph.crypto.core.entity.User;
import fr.cph.crypto.core.exception.NotAllowedException;
import fr.cph.crypto.core.spi.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class LoginUserTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private LoginUser loginUser = new LoginUser(userRepository);

	@Test
	void testLoginUser() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserByEmail("username")).willReturn(user);

		// when
		User actual = loginUser.login("username");

		// then
		then(userRepository).should().findOneUserByEmail("username");
		assertNotNull(actual);
	}

	@Test
	void testLoginUserNotFound() {
		// given
		given(userRepository.findOneUserByEmail("username")).willReturn(null);

		// when
		Executable actualExecutable = () -> loginUser.login("username");

		// then
		assertThrows(NotAllowedException.class, actualExecutable);
		then(userRepository).should().findOneUserByEmail("username");
	}

	@Test
	void testLoginUserNotAllowed() {
		// given
		User user = Utils.getUser();
		user.setAllowed(false);
		given(userRepository.findOneUserByEmail("username")).willReturn(user);

		// when
		Executable actualExecutable = () -> loginUser.login("username");

		// then
		assertThrows(NotAllowedException.class, actualExecutable);
		then(userRepository).should().findOneUserByEmail("username");
	}
}
