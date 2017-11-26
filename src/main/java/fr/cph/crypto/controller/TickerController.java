package fr.cph.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import fr.cph.crypto.domain.Currency;
import fr.cph.crypto.domain.Ticker;
import fr.cph.crypto.repository.TickerRepository;

@RequestMapping(value = "/ticker")
@RestController
public class TickerController {

	private final TickerRepository repository;

	@Autowired
	public TickerController(final TickerRepository repository) {
		this.repository = repository;
	}

	@RequestMapping
	public List<Ticker> getAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{baseCurrency}/{quoteCurrency}")
	public Ticker getTicker(
			@PathVariable("baseCurrency") final Currency baseCurrency,
			@PathVariable("quoteCurrency") final Currency quoteCurrency) {
		return repository.findOne(baseCurrency.getCode() + "-" + quoteCurrency.getCode());
	}
}
