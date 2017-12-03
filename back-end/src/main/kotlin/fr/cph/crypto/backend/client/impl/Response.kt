package fr.cph.crypto.backend.client.impl

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "name", "symbol", "rank", "price_usd", "price_btc", "24h_volume_usd", "market_cap_usd", "available_supply", "total_supply", "max_supply", "percent_change_1h", "percent_change_24h", "percent_change_7d", "last_updated", "price_eur", "24h_volume_eur", "market_cap_eur")
class Response {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: String? = null
    @JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null
    @JsonProperty("symbol")
    @get:JsonProperty("symbol")
    @set:JsonProperty("symbol")
    var symbol: String? = null
    @JsonProperty("rank")
    @get:JsonProperty("rank")
    @set:JsonProperty("rank")
    var rank: String? = null
    @JsonProperty("price_usd")
    @get:JsonProperty("price_usd")
    @set:JsonProperty("price_usd")
    var priceUsd: String? = null
    @JsonProperty("price_btc")
    @get:JsonProperty("price_btc")
    @set:JsonProperty("price_btc")
    var priceBtc: String? = null
    @JsonProperty("24h_volume_usd")
    @get:JsonProperty("24h_volume_usd")
    @set:JsonProperty("24h_volume_usd")
    var _24hVolumeUsd: String? = null
    @JsonProperty("market_cap_usd")
    @get:JsonProperty("market_cap_usd")
    @set:JsonProperty("market_cap_usd")
    var marketCapUsd: String? = null
    @JsonProperty("available_supply")
    @get:JsonProperty("available_supply")
    @set:JsonProperty("available_supply")
    var availableSupply: String? = null
    @JsonProperty("total_supply")
    @get:JsonProperty("total_supply")
    @set:JsonProperty("total_supply")
    var totalSupply: String? = null
    @JsonProperty("max_supply")
    @get:JsonProperty("max_supply")
    @set:JsonProperty("max_supply")
    var maxSupply: String? = null
    @JsonProperty("percent_change_1h")
    @get:JsonProperty("percent_change_1h")
    @set:JsonProperty("percent_change_1h")
    var percentChange1h: String? = null
    @JsonProperty("percent_change_24h")
    @get:JsonProperty("percent_change_24h")
    @set:JsonProperty("percent_change_24h")
    var percentChange24h: String? = null
    @JsonProperty("percent_change_7d")
    @get:JsonProperty("percent_change_7d")
    @set:JsonProperty("percent_change_7d")
    var percentChange7d: String? = null
    @JsonProperty("last_updated")
    @get:JsonProperty("last_updated")
    @set:JsonProperty("last_updated")
    var lastUpdated: String? = null
    @JsonProperty("price_eur")
    @get:JsonProperty("price_eur")
    @set:JsonProperty("price_eur")
    var priceEur: String? = null
    @JsonProperty("24h_volume_eur")
    @get:JsonProperty("24h_volume_eur")
    @set:JsonProperty("24h_volume_eur")
    var _24hVolumeEur: String? = null
    @JsonProperty("market_cap_eur")
    @get:JsonProperty("market_cap_eur")
    @set:JsonProperty("market_cap_eur")
    var marketCapEur: String? = null
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        this.additionalProperties.put(name, value)
    }

}