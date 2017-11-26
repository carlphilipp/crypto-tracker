package fr.cph.crypto.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Position {

	@Id
	private String id;

	private Currency currency;
	private Double quantity;
	private Double costPrice;
	private Currency costPriceCurrency;

	// Calculated fields
	private Double originalValue;
	private Double value;
	private Double gain;
	private Double gainPercentage;
}
