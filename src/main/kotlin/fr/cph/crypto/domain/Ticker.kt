package fr.cph.crypto.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency", "price", "percentChange1h", "percentChange24h", "percentChange7d", "lastUpdated")
@Document
data class Ticker(val currency: Currency,
                  val price: Double,
                  val percentChange1h: Double,
                  val percentChange24h: Double,
                  val percentChange7d: Double,
                  val lastUpdated: String) : Serializable {

    @Id
    var id: String? = null
}
