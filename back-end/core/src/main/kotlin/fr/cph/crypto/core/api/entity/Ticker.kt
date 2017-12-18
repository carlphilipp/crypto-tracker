package fr.cph.crypto.core.api.entity

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