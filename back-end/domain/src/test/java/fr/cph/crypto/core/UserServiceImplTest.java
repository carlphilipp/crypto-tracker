package fr.cph.crypto.core;

import fr.cph.crypto.core.api.entity.Currency;
import fr.cph.crypto.core.api.entity.Position;
import fr.cph.crypto.core.api.entity.User;
import fr.cph.crypto.core.core.UserServiceImpl;
import fr.cph.crypto.core.spi.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class UserServiceImplTest {
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private ShareValueRepository shareValueRepository = Mockito.mock(ShareValueRepository.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private IdGenerator idGenerator = Mockito.mock(IdGenerator.class);
	private PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
	private UserServiceImpl tickerService = new UserServiceImpl(
			userRepository = userRepository,
			shareValueRepository = shareValueRepository,
			tickerRepository = tickerRepository,
			idGenerator = idGenerator,
			passwordEncoder = passwordEncoder);


	@Test
	void testAddPosition() {
		// given
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		User user = Utils.getUser();
		Position ethPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 10.0, 200.0, null, null, null, null, null);
		user.getPositions().add(ethPosition);

		Position btcPosition = new Position("BTC-USD", Currency.BTC, Currency.USD, 1.0, 5000.0, null, null, null, null, null);
		given(userRepository.findOneUserById("id")).willReturn((user));
		given(idGenerator.getNewId()).willReturn(("positionId"));

		// when
		tickerService.addPosition("id", btcPosition);

		// then
		then(userRepository).should().findOneUserById("id");
		then(idGenerator).should().getNewId();
		then(userRepository).should().savePosition(userCaptor.capture(), eq(btcPosition));
		User actualUser = userCaptor.getValue();
		assertEquals(5000.0, actualUser.getLiquidityMovement());
		assertEquals(Currency.BTC, actualUser.getPositions().get(0).getCurrency1());
		assertEquals("positionId", actualUser.getPositions().get(0).getId());
		assertEquals(Currency.ETH, actualUser.getPositions().get(1).getCurrency1());
	}
}
