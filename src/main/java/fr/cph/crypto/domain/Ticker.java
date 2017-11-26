package fr.cph.crypto.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Ticker implements Serializable {

	@Id
	private String id;
	private Currency currency;
	private Double price;
	private Double percentChange1h;
	private Double percentChange24h;
	private Double percentChange7d;
	private String lastUpdated; // change to date
}
