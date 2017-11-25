package fr.cph.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.cph.crypto.domain.Currency;
import fr.cph.crypto.domain.FiatCurrency;
import fr.cph.crypto.domain.Ticker;
import fr.cph.crypto.repository.TickerRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Autowired
	private TickerRepository repository;

	@Override
	public void run(String... strings) throws Exception {
		repository.save(new Ticker("BTC", FiatCurrency.USD,"Bitcoin", 0.0, 0.0, 0.0, 0.0, ""));
	}
}
