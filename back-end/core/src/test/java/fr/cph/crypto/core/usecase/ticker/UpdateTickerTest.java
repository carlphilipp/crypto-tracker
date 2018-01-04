package fr.cph.crypto.core.usecase.ticker;

import fr.cph.crypto.core.entity.Currency;
import fr.cph.crypto.core.entity.Ticker;
import fr.cph.crypto.core.spi.TickerClient;
import fr.cph.crypto.core.spi.TickerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class UpdateTickerTest {

	private TickerClient tickerClient = Mockito.mock(TickerClient.class);
	private TickerRepository tickerRepository = Mockito.mock(TickerRepository.class);
	private UpdateTicker updateTicker = new UpdateTicker(tickerRepository, tickerClient);

	@Test
	void testUpdateAll() {
		// given
		Ticker ticker = new Ticker(null, Currency.BTC, Currency.USD, 1.0, "exchange", 0.0, 0.0, 0.0, 0.0, 0.0, 0L);
		given(tickerClient.getTickers(Currency.USD, Currency.Companion.cryptoCurrenciesAsListOfString())).willReturn(Collections.singletonList(ticker));

		// when
		updateTicker.updateAll();

		// then
		then(tickerClient).should().getTickers(Currency.USD, Currency.Companion.cryptoCurrenciesAsListOfString());
		then(tickerRepository).should().save(Collections.singletonList(ticker));
	}
}
