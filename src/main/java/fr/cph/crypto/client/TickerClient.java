package fr.cph.crypto.client;

import java.util.List;
import java.util.Optional;

import fr.cph.crypto.domain.Currency;
import fr.cph.crypto.domain.Ticker;

public interface TickerClient {

	List<Ticker> getTickers(Currency currency, String... tickers);

	List<Ticker> getTickers(Currency currency, List<String> tickers);

	Optional<Ticker> getTicker(Currency currency, String ticker);
}
