/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.core.entity

import java.util.*

enum class Currency constructor(val code: String, val currencyName: String, val symbol: String, val type: Type) {
	BTC("BTC", "Bitcoin", "฿", Type.CRYPTO),
	ETH("ETH", "Ethereum", "Ξ", Type.CRYPTO),
	GRS("GRS", "Groestlcoin", "GRS", Type.CRYPTO),
	LTC("LTC", "Litecoin", "Ł", Type.CRYPTO),
	VTC("VTC", "Vertcoin", "VTC", Type.CRYPTO),
	ETHOS("ETHOS", "Ethos", "ETHOS", Type.CRYPTO),
	CARDANO("ADA", "Cardano", "ADA", Type.CRYPTO),
	POWER_LEDGER("POWR", "Power Ledger", "POWR", Type.CRYPTO),
	ICON("ICX", "Icon", "ICX", Type.CRYPTO),
	MONERO("XMR", "Monero", "XMR", Type.CRYPTO),
	NEO("NEO", "NEO", "NEO", Type.CRYPTO),
	EOS("EOS", "EOS", "EOS", Type.CRYPTO),
	STEEM("STEEM", "Steem", "STEEM", Type.CRYPTO),
	KOMODO("KMD", "Komodo", "KMD", Type.CRYPTO),
	ARK("ARK", "Ark", "ARK", Type.CRYPTO),
	WALTON("WTC", "Walton", "WTC", Type.CRYPTO),
	NAV("NAV", "Nav Coin", "NAV", Type.CRYPTO),
	UTRUST("UTK", "Utrust", "UTK", Type.CRYPTO),
	XRB("XRB", "RaiBlocks", "XRB", Type.CRYPTO),
	XLM("XLM", "Stellar", "XLM", Type.CRYPTO),

	USD("USD", "United States Dollar", "$", Type.FIAT),
	EUR("EUR", "Euro", "€", Type.FIAT),
	UNKNOWN("UNKNOWN", "Unknown", "U", Type.FIAT);

	internal enum class Type {
		FIAT,
		CRYPTO
	}

	companion object {
		fun findCurrency(code: String): Currency {
			return Arrays.stream(Currency.values())
				.filter { currency -> currency.code == code }
				.findAny()
				.orElse(UNKNOWN)
		}

		fun cryptoCurrenciesAsListOfString(): List<String> {
			return Currency.values()
				.filter { currency -> currency.type == Type.CRYPTO }
				.map { currency -> currency.code }
				.toList()
		}
	}
}
