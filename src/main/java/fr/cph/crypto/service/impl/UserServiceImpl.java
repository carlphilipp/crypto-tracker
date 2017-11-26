package fr.cph.crypto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cph.crypto.client.impl.CoinMarketCapClient;
import fr.cph.crypto.domain.Currency;
import fr.cph.crypto.domain.Position;
import fr.cph.crypto.domain.Ticker;
import fr.cph.crypto.repository.PositionRepository;
import fr.cph.crypto.repository.TickerRepository;
import fr.cph.crypto.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CoinMarketCapClient client;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private TickerRepository tickerRepository;

	@Override
	public Position updatePosition(final Position position) {

		// Update ticker in DB from client
		client.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
				.stream()
				.forEach(ticker -> {
					tickerRepository.save(ticker);
				});
		final Ticker ticker = tickerRepository.findOne(position.getCurrency() + "-" + position.getCostPriceCurrency());
		////////////////////////////////////

		final Double quantity = position.getQuantity();
		final Double costPrice = position.getCostPrice();
		final Double originalValue = quantity * costPrice;
		final Double value = quantity * ticker.getPrice();
		final Double gain = value - originalValue;
		final Double gainPercentage = ((value * 100) / originalValue) - 100;

		position.setOriginalValue(originalValue);
		position.setValue(value);
		position.setGain(gain);
		position.setGainPercentage(gainPercentage);

		positionRepository.save(position);
		return position;
	}
}
