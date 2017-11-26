package fr.cph.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cph.crypto.client.impl.CoinMarketCapClient;
import fr.cph.crypto.domain.Currency;
import fr.cph.crypto.repository.TickerRepository;

@RestController
public class RefreshController {

	@Autowired
	private TickerRepository repository;
	@Autowired
	private CoinMarketCapClient client;

	@RequestMapping(value = "/refresh")
	public ResponseEntity<String> refreshAll() {
		client.getTickers(Currency.USD, "BTC", "ETH", "LTC", "VTC", "GRS")
				.stream()
				.forEach(ticker -> {
					repository.save(ticker);
				});
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
