package fr.cph.crypto.core.usecase.ticker;

import fr.cph.crypto.core.entity.Currency;
import fr.cph.crypto.core.entity.Ticker;
import fr.cph.crypto.core.spi.TickerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class FindTickerTest {

	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private FindTicker findTicker = new FindTicker(tickerRepository);

	@Test
	void testFindOne() {
		// given
		String tickerId = "BTC-USD";
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerRepository.findOne(tickerId)).willReturn(ticker);

		// when
		Ticker actual = findTicker.findOne(tickerId);

		// then
		then(tickerRepository).should().findOne(tickerId);
		assertEquals(actual, ticker);
	}

	@Test
	void testFindAllById() {
		// given
		List<String> tickerIds = Collections.singletonList("BTC-USD");
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerRepository.findAllById(tickerIds)).willReturn(Collections.singletonList(ticker));

		// when
		List<Ticker> actual = findTicker.findAllById(tickerIds);

		// then
		then(tickerRepository).should().findAllById(tickerIds);
		assertEquals(actual.get(0), ticker);
	}

	@Test
	void testFindAll() {
		// given
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerRepository.findAll()).willReturn(Collections.singletonList(ticker));

		// when
		List<Ticker> actual = findTicker.findAll();

		// then
		then(tickerRepository).should().findAll();
		assertEquals(actual.get(0), ticker);
	}
}
