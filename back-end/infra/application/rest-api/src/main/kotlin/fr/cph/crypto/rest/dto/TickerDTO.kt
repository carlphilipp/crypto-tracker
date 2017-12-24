/**
 * Copyright 2017 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.rest.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.cph.crypto.core.api.entity.Ticker

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "currency1", "currency2", "price", "exchange", "volume24h", "marketCap", "percentChange1h", "percentChange24h", "percentChange7d", "lastUpdated")
data class TickerDTO(var id: String? = null,
                     val currency1: CurrencyDTO,
                     val currency2: CurrencyDTO,
                     val price: Double,
                     val exchange: String,
                     val volume24h: Double,
                     val marketCap: Double,
                     val percentChange1h: Double,
                     val percentChange24h: Double,
                     val percentChange7d: Double,
                     val lastUpdated: Long) {
    companion object {
        fun from(ticker: Ticker): TickerDTO {
            return TickerDTO(
                    id = ticker.id,
                    currency1 = CurrencyDTO.from(ticker.currency1),
                    currency2 = CurrencyDTO.from(ticker.currency2),
                    price = ticker.price,
                    exchange = ticker.exchange,
                    volume24h = ticker.volume24h,
                    marketCap = ticker.marketCap,
                    percentChange1h = ticker.percentChange1h,
                    percentChange24h = ticker.percentChange24h,
                    percentChange7d = ticker.percentChange7d,
                    lastUpdated = ticker.lastUpdated)
        }
    }
}