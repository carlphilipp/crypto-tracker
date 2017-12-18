package fr.cph.crypto.client.coinmarketcap

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response(
        var id: String? = null,
        var name: String? = null,
        var symbol: String? = null,
        var rank: String? = null,
        @JsonProperty("price_usd")
        var priceUsd: String? = null,
        @JsonProperty("price_btc")
        var priceBtc: String? = null,
        @JsonProperty("24h_volume_usd")
        var _24hVolumeUsd: String? = null,
        @JsonProperty("market_cap_usd")
        var marketCapUsd: String? = null,
        @JsonProperty("available_supply")
        var availableSupply: String? = null,
        @JsonProperty("total_supply")
        var totalSupply: String? = null,
        @JsonProperty("max_supply")
        var maxSupply: String? = null,
        @JsonProperty("percent_change_1h")
        var percentChange1h: String? = null,
        @JsonProperty("percent_change_24h")
        var percentChange24h: String? = null,
        @JsonProperty("percent_change_7d")
        var percentChange7d: String? = null,
        @JsonProperty("last_updated")
        var lastUpdated: String? = null
)