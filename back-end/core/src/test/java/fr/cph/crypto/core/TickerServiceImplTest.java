package fr.cph.crypto.core;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import fr.cph.crypto.core.api.entity.Currency;
import fr.cph.crypto.core.api.entity.Ticker;
import fr.cph.crypto.core.core.TickerServiceImpl;
import fr.cph.crypto.core.spi.TickerClient;
import fr.cph.crypto.core.spi.TickerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class TickerServiceImplTest {

	private TickerClient tickerClient = Mockito.mock(TickerClient.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private TickerServiceImpl tickerService = new TickerServiceImpl(tickerClient, tickerRepository);

	@Test
	public void testFindOne() {
		// given
		String tickerId = "BTC-USD";
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerRepository.findOne(tickerId)).willReturn(ticker);

		// when
		Ticker actual = tickerService.findOne(tickerId);

		// then
		then(tickerRepository).should().findOne(tickerId);
		assertEquals(actual, ticker);
	}

	@Test
	public void testFindAllById() {
		// given
		List<String> tickerIds = Collections.singletonList("BTC-USD");
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerRepository.findAllById(tickerIds)).willReturn(Collections.singletonList(ticker));

		// when
		List<Ticker> actual = tickerService.findAllById(tickerIds);

		// then
		then(tickerRepository).should().findAllById(tickerIds);
		assertEquals(actual.get(0), ticker);
	}

	@Test
	public void testFindAll() {
		// given
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerRepository.findAll()).willReturn(Collections.singletonList(ticker));

		// when
		List<Ticker> actual = tickerService.findAll();

		// then
		then(tickerRepository).should().findAll();
		assertEquals(actual.get(0), ticker);
	}

	@Test
	public void testUpdateAll() {
		// given
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerClient.getTickers(Currency.USD, Currency.Companion.cryptoCurrenciesAsListOfString())).willReturn(Collections.singletonList(ticker));

		// when
		tickerService.updateAll();

		// then
		then(tickerClient).should().getTickers(Currency.USD, Currency.Companion.cryptoCurrenciesAsListOfString());
		then(tickerRepository).should().save(ticker);
	}
}
