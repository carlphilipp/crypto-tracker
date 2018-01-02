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
package fr.cph.crypto.persistence.mongo.entity

import fr.cph.crypto.core.entity.Currency
import fr.cph.crypto.core.entity.Ticker
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "ticker")
data class TickerDB(@Id var id: String? = null,
					@Indexed val currency1: Currency,
					val currency2: Currency,
					val price: Double,
					val exchange: String,
					val volume24h: Double,
					val marketCap: Double,
					val percentChange1h: Double,
					val percentChange24h: Double,
					val percentChange7d: Double,
					val lastUpdated: Long) {

    fun toTicker(): Ticker {
        val ticker = Ticker(
				currency1 = this.currency1,
				currency2 = this.currency2,
				price = this.price,
				exchange = this.exchange,
				volume24h = this.volume24h,
				marketCap = this.marketCap,
				percentChange1h = this.percentChange1h,
				percentChange7d = this.percentChange7d,
				percentChange24h = this.percentChange24h,
				lastUpdated = this.lastUpdated
		)
        ticker.id = this.id
        return ticker
    }

    companion object {
        fun from(ticker: Ticker): TickerDB {
            return TickerDB(
                    id = ticker.id,
                    currency1 = ticker.currency1,
                    currency2 = ticker.currency2,
                    price = ticker.price,
                    exchange = ticker.exchange,
                    volume24h = ticker.volume24h,
                    marketCap = ticker.marketCap,
                    percentChange1h = ticker.percentChange1h,
                    percentChange7d = ticker.percentChange7d,
                    percentChange24h = ticker.percentChange24h,
                    lastUpdated = ticker.lastUpdated
            )
        }
    }
}