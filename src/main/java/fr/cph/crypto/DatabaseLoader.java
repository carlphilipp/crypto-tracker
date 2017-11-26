package fr.cph.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import fr.cph.crypto.domain.Currency;
import fr.cph.crypto.domain.Position;
import fr.cph.crypto.domain.Ticker;
import fr.cph.crypto.domain.User;
import fr.cph.crypto.repository.PositionRepository;
import fr.cph.crypto.repository.TickerRepository;
import fr.cph.crypto.repository.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private TickerRepository tickerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PositionRepository positionRepository;

	@Override
	public void run(String... strings) throws Exception {
		tickerRepository.deleteAll();
		userRepository.deleteAll();
		//positionRepository.deleteAll();
		Ticker ticker = new Ticker("BTC-USD", Currency.BTC, 6000.0, 0.0, 0.0, 0.0, "");

		tickerRepository.save(ticker);

		List<Position> positions = new ArrayList<>();

		final Double quantity = 2.0;
		final Double costPrice = 5000.0;
		final Double originalValue = quantity * costPrice;
		final Double value = quantity * ticker.getPrice();
		final Double gain = value - originalValue;
		final Double gainPercentage = ((value * 100) / originalValue) - 100;
		final Position position = new Position(null, Currency.BTC, quantity, 5000.0, Currency.USD, originalValue, value, gain, gainPercentage);
		positions.add(position);
		positionRepository.save(position);
		userRepository.save(new User(null, "cp.harmant@gmail.com", positions));
	}
}
