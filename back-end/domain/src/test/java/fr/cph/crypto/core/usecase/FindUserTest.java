package fr.cph.crypto.core.usecase;

import fr.cph.crypto.core.Utils;
import fr.cph.crypto.core.api.entity.Currency;
import fr.cph.crypto.core.api.entity.Position;
import fr.cph.crypto.core.api.entity.Ticker;
import fr.cph.crypto.core.api.entity.User;
import fr.cph.crypto.core.spi.TickerRepository;
import fr.cph.crypto.core.spi.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class FindUserTest {
	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private FindUser findUser = new FindUser(userRepository = userRepository, tickerRepository = tickerRepository);

	@Test
	void testFindOneUserWithPositions() {
		// given
		Ticker btcTicker = new Ticker("BTC-USD", Currency.BTC, Currency.USD, 10000.0, "whatever", 0.0, 0.0, 0.0, 0.0, 0.0, 1L);
		Ticker ethTicker = new Ticker("ETH-USD", Currency.ETH, Currency.USD, 100.0, "whatever", 0.0, 0.0, 0.0, 0.0, 0.0, 2L);
		Position btcPosition = new Position("BTC-USD", Currency.BTC, Currency.USD, 1.0, 5000.0, null, null, null, null, null);
		Position ethPosition = new Position("ETH-USD", Currency.ETH, Currency.USD, 10.0, 200.0, null, null, null, null, null);
		List<Position> positions = Arrays.asList(btcPosition, ethPosition);
		User user = Utils.getUser();
		user.setPositions(positions);
		given(userRepository.findOneUserById("id")).willReturn(user);
		given(tickerRepository.findAllById(Arrays.asList("BTC-USD", "ETH-USD"))).willReturn(Arrays.asList(btcTicker, ethTicker));

		// when
		User actual = findUser.findOne("id");

		// then
		then(userRepository).should().findOneUserById("id");
		then(tickerRepository).should().findAllById(Arrays.asList("BTC-USD", "ETH-USD"));
		assertEquals(user, actual);
		assertEquals(11000.0, actual.getValue().doubleValue());
		assertEquals(7000.0, actual.getOriginalValue().doubleValue());
		assertEquals(4000.0, actual.getGain().doubleValue());
		assertEquals(0.5714285714285714, actual.getGainPercentage().doubleValue());
		assertEquals(10000.0, actual.getPositions().get(0).getValue().doubleValue());
		assertEquals(5000.0, actual.getPositions().get(0).getOriginalValue().doubleValue());
		assertEquals(5000.0, actual.getPositions().get(0).getGain().doubleValue());
		assertEquals(1.0, actual.getPositions().get(0).getGainPercentage().doubleValue());
		assertEquals(1L, actual.getPositions().get(0).getLastUpdated().longValue());
		assertEquals(1000.0, actual.getPositions().get(1).getValue().doubleValue());
		assertEquals(2000.0, actual.getPositions().get(1).getOriginalValue().doubleValue());
		assertEquals(-1000.0, actual.getPositions().get(1).getGain().doubleValue());
		assertEquals(-0.5, actual.getPositions().get(1).getGainPercentage().doubleValue());
		assertEquals(2L, actual.getPositions().get(1).getLastUpdated().longValue());
	}

	@Test
	void testFindOneUserNoPositions() {
		// given
		User user = Utils.getUser();
		given(userRepository.findOneUserById("id")).willReturn(user);

		// when
		User actual = findUser.findOne("id");

		// then
		then(userRepository).should().findOneUserById("id");
		assertEquals(user, actual);
		assertEquals(0.0, actual.getValue().doubleValue());
		assertEquals(0.0, actual.getOriginalValue().doubleValue());
		assertEquals(0.0, actual.getGain().doubleValue());
		assertEquals(0.0, actual.getGainPercentage().doubleValue());
	}

	@Test
	void testFindAll() {
		// given
		User user = Utils.getUser();
		given(userRepository.findAllUsers()).willReturn(Arrays.asList(user));

		// when
		List<User> actual = findUser.findAll();

		// then
		then(userRepository).should().findAllUsers();
		assertEquals(user, actual.get(0));
		assertEquals(0.0, actual.get(0).getValue().doubleValue());
		assertEquals(0.0, actual.get(0).getOriginalValue().doubleValue());
		assertEquals(0.0, actual.get(0).getGain().doubleValue());
		assertEquals(0.0, actual.get(0).getGainPercentage().doubleValue());
	}
}
