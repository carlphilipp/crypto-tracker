package fr.cph.crypto.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
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
