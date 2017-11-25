package fr.cph.crypto.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@IdClass(TickerId.class)
public class Ticker implements Serializable {

	@Id
	private String symbol;
	@Id
	private FiatCurrency currency;
	private String name;
	private Double price;
	private Double percentChange1h;
	private Double percentChange24h;
	private Double percentChange7d;
	private String lastUpdated; // change to date
}
