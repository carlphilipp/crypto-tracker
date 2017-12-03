package fr.cph.crypto.backend.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "price", "exchange", "percentChange1h", "percentChange24h", "percentChange7d", "lastUpdated")
@Document
data class Ticker(@Indexed val currency1: Currency,
                  val currency2: Currency,
                  val price: Double,
                  val exchange: String,
                  val percentChange1h: Double,
                  val percentChange24h: Double,
                  val percentChange7d: Double,
                  val lastUpdated: Long) {

    @Id
    var id: String? = null
}
