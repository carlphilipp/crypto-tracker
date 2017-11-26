package fr.cph.crypto.client.impl;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.cph.crypto.domain.Currency;
import fr.cph.crypto.domain.Ticker;

import static fr.cph.crypto.domain.Currency.EUR;
import static fr.cph.crypto.domain.Currency.USD;

@Mapper
public interface TickerMapper {

	TickerMapper INSTANCE = Mappers.getMapper(TickerMapper.class);

	Ticker responseToTicker(Response response);

	default Ticker responseToTicker(final Response response, final Currency currency) {
		final Ticker ticker = responseToTicker(response);
		ticker.setId(response.getSymbol() + "-" + currency.name());
		ticker.setCurrency(Currency.findCurrency(response.getSymbol()));
		if (currency == USD) {
			ticker.setPrice(Double.valueOf(response.getPriceUsd()));
		} else if (currency == EUR) {
			ticker.setPrice(Double.valueOf(response.getPriceEur()));
		}
		return ticker;
	}
}


