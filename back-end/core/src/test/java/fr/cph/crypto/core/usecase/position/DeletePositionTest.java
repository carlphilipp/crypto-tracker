package fr.cph.crypto.core.usecase.position;

import fr.cph.crypto.core.Utils;
import fr.cph.crypto.core.entity.Currency;
import fr.cph.crypto.core.entity.Position;
import fr.cph.crypto.core.entity.User;
import fr.cph.crypto.core.exception.NotAllowedException;
import fr.cph.crypto.core.exception.UserNotFoundException;
import fr.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class DeletePositionTest {
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private DeletePosition deletePosition = new DeletePosition(userRepository);

	@Test
	void testDeletePosition() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position position1 = new Position("positionId", Currency.BTC, Currency.USD, 1, 1, null, null, null, null, null);
		user.getPositions().add(position1);
		user.getPositions().add(new Position("positionId2", Currency.BTC, Currency.USD, 1, 1, null, null, null, null, null));
		given(userRepository.findOneUserById("id")).willReturn(user);

		// when
		deletePosition.deletePosition("id", "positionId", 10);

		// then
		then(userRepository).should().findOneUserById("id");
		then(userRepository).should().deletePosition(userCaptor.capture(), eq(position1));
		assertEquals(-10, userCaptor.getValue().getLiquidityMovement());
	}

	@Test
	void testDeletePositionRunTime() {
		// given
		User user = Utils.getUser();
		Position position1 = new Position("positionId", Currency.BTC, Currency.USD, 1, 1, null, null, null, null, null);
		user.getPositions().add(position1);
		user.getPositions().add(new Position("positionId2", Currency.BTC, Currency.USD, 1, 1, null, null, null, null, null));
		given(userRepository.findOneUserById("id")).willReturn(user);

		// when
		deletePosition.deletePosition("id", "positionId", 0);

		// then
		then(userRepository).should().findOneUserById("id");
		then(userRepository).should().deletePosition(user, position1);
	}

	@Test
	void testDeletePositionUserNotFound() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserById("id")).willReturn(null);

		// when
		Executable actualExecutable = () -> deletePosition.deletePosition("id", "positionId", 0);

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [id] not found");
		then(userRepository).should().findOneUserById("id");
	}

	@Test
	void testDeletePositionNotFound() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserById("id")).willReturn(user);

		// when
		Executable actualExecutable = () -> deletePosition.deletePosition("id", "positionId", 0);

		// then
		assertThrows(NotAllowedException.class, actualExecutable);
		then(userRepository).should().findOneUserById("id");
	}
}
