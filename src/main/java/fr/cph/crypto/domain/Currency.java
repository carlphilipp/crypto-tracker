package fr.cph.crypto.domain;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Currency {
	BTC("Bitcoin", "฿", Type.CRYPTO),
	ETH("Ether", "Ξ", Type.CRYPTO),
	GRS("Groestlcoin", "GRS", Type.CRYPTO),
	LTC("Litecoin", "Ł", Type.CRYPTO),
	VTC("Vertcoin", "VTC", Type.CRYPTO),

	USD("United States Dollar", "$", Type.FIAT),
	EUR("Euro", "€", Type.FIAT),
	UNKNOWN("Unknown", "U", Type.FIAT);

	private final String name;
	private final String symbol;
	private final Type type;

	Currency(String name, String symbol, Type type) {
		this.name = name;
		this.symbol = symbol;
		this.type = type;
	}

	public String getCode() {
		return this.name();
	}

	public static Currency findCurrency(final String str) {
		return Arrays.stream(Currency.values())
				.filter(currency -> currency.name().equals(str))
				.findAny()
				.orElse(Currency.UNKNOWN);
	}

	enum Type {
		FIAT,
		CRYPTO
	}
}
