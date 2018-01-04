package fr.cph.crypto.core.usecase.position;

import fr.cph.crypto.core.Utils;
import fr.cph.crypto.core.entity.Currency;
import fr.cph.crypto.core.entity.Position;
import fr.cph.crypto.core.entity.User;
import fr.cph.crypto.core.exception.PositionNotFoundException;
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

class UpdatePositionTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private UpdatePosition updatePosition = new UpdatePosition(userRepository);

	@Test
	void testUpdatePositionManualUserNotFound() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position newPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 20.0, 250, null, null, null, null, null);
		given(userRepository.findOneUserById("userId")).willReturn((null));

		// when
		Executable actualExecutable = () -> updatePosition.updatePosition("userId", newPosition, null, null);

		// then
		assertThrows(UserNotFoundException.class, actualExecutable, "User id [userId] not found");
		then(userRepository).should().findOneUserById("userId");
	}

	@Test
	void testUpdatePositionManual() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position oldPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 10.0, 200.0, null, null, null, null, null);
		user.getPositions().add(oldPosition);
		user.setLiquidityMovement(1000);
		Position newPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 20.0, 250, null, null, null, null, null);
		given(userRepository.findOneUserById("userId")).willReturn((user));

		// when
		updatePosition.updatePosition("userId", newPosition, null, null);

		// then
		then(userRepository).should().findOneUserById("userId");
		then(userRepository).should().savePosition(userCaptor.capture(), eq(newPosition));
		User actualUser = userCaptor.getValue();
		assertEquals(4000.0, actualUser.getLiquidityMovement());
	}

	@Test
	void testUpdatePositionManualNotFound() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position newPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 20.0, 250, null, null, null, null, null);
		given(userRepository.findOneUserById("userId")).willReturn((user));

		// when
		Executable actualExecutable = () -> updatePosition.updatePosition("userId", newPosition, null, null);

		// then
		assertThrows(PositionNotFoundException.class, actualExecutable, "Position id [ETH-USD] not found");
		then(userRepository).should().findOneUserById("userId");
	}

	@Test
	void testUpdatePositionSmart() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position oldPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 10.0, 200.0, null, null, null, null, null);
		user.getPositions().add(oldPosition);
		user.setLiquidityMovement(1000);
		Position newPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 20.0, 250, null, null, null, null, null);
		given(userRepository.findOneUserById("userId")).willReturn((user));

		// when
		updatePosition.updatePosition("userId", newPosition, 20.0, 250.0);

		// then
		then(userRepository).should().findOneUserById("userId");
		then(userRepository).should().savePosition(userCaptor.capture(), eq(newPosition));
		User actualUser = userCaptor.getValue();
		assertEquals(6000.0, actualUser.getLiquidityMovement());
	}

	@Test
	void testUpdatePositionSmartNotFound() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		user.setLiquidityMovement(1000);
		Position newPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 20.0, 250, null, null, null, null, null);
		given(userRepository.findOneUserById("userId")).willReturn((user));

		// when
		Executable actualExecutable = () -> updatePosition.updatePosition("userId", newPosition, 20.0, 150.0);

		// then
		assertThrows(PositionNotFoundException.class, actualExecutable, "Position id [ETH-USD] not found");
		then(userRepository).should().findOneUserById("userId");
	}
}
