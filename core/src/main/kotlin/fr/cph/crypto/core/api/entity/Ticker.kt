package fr.cph.crypto.core.api.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "price", "exchange", "volume24h", "marketCap", "percentChange1h", "percentChange24h", "percentChange7d", "lastUpdated")
data class Ticker(var id: String? = null,
                  val currency1: Currency,
                  val currency2: Currency,
                  val price: Double,
                  val exchange: String,
                  val volume24h: Double,
                  val marketCap: Double,
                  val percentChange1h: Double,
                  val percentChange24h: Double,
                  val percentChange7d: Double,
                  val lastUpdated: Long)