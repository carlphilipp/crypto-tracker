package fr.cph.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.cph.crypto.domain.FiatCurrency;
import fr.cph.crypto.domain.Ticker;
import fr.cph.crypto.domain.User;
import fr.cph.crypto.repository.TickerRepository;
import fr.cph.crypto.repository.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private TickerRepository tickerRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... strings) throws Exception {
		tickerRepository.save(new Ticker("BTC", FiatCurrency.USD, "Bitcoin", 0.0, 0.0, 0.0, 0.0, 0.0, ""));

		userRepository.save(new User(null, "cp.harmant@gmail.com"));
	}
}
