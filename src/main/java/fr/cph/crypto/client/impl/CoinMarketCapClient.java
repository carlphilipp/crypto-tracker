package fr.cph.crypto.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.cph.crypto.client.TickerClient;
import fr.cph.crypto.domain.FiatCurrency;
import fr.cph.crypto.domain.Ticker;

@Component
public class CoinMarketCapClient implements TickerClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Optional<Ticker> getTicker(final FiatCurrency currency, final String ticker) {
		return getAllTickers(currency).stream()
				.filter(t -> t.getSymbol().equals(ticker))
				.findAny();
	}

	@Override
	public List<Ticker> getTickers(final FiatCurrency currency, String... tickers) {
		return getTickers(currency, Arrays.asList(tickers));
	}

	@Override
	public List<Ticker> getTickers(final FiatCurrency currency, final List<String> tickers) {
		return getAllTickers(currency).stream()
				.filter(ticker -> tickers.contains(ticker.getSymbol()))
				.collect(Collectors.toList());
	}

	private List<Ticker> getAllTickers(final FiatCurrency currency) {
		final UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.coinmarketcap.com")
				.path("/v1/ticker")
				.queryParam("convert", currency.toString())
				.build();

		final Response[] responses = restTemplate.getForObject(uriComponents.toUri(), Response[].class);
		return Arrays.stream(responses)
				.map(response -> TickerMapper.INSTANCE.responseToTicker(response, currency))
				.collect(Collectors.toList());
	}
}
