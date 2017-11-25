package fr.cph.crypto.client;

import java.util.List;
import java.util.Optional;

import fr.cph.crypto.domain.FiatCurrency;
import fr.cph.crypto.domain.Ticker;

public interface TickerClient {

	List<Ticker> getTickers(FiatCurrency currency, String... tickers);

	List<Ticker> getTickers(FiatCurrency currency, List<String> tickers);

	Optional<Ticker> getTicker(FiatCurrency currency, String ticker);
}
