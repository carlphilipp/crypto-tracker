package fr.cph.crypto.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TickerId implements Serializable {
	String symbol;
	FiatCurrency currency;
}