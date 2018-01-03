/**
 * Copyright 2018 Carl-Philipp Harmant
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
package fr.cph.crypto.config

import fr.cph.crypto.client.coinmarketcap.CoinMarketCapAdapter
import fr.cph.crypto.core.spi.EmailService
import fr.cph.crypto.core.spi.IdGenerator
import fr.cph.crypto.core.spi.TemplateService
import fr.cph.crypto.core.spi.TickerClient
import fr.cph.crypto.email.DefaultEmailAdapter
import fr.cph.crypto.template.DefaultTemplateAdapter
import fr.cph.crypto.uuid.jug.Jug
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class InfraConfig {

	@Bean
	fun uuidGenerator(): IdGenerator {
		return Jug()
	}

	@Bean
	fun emailService(): EmailService {
		return DefaultEmailAdapter()
	}

	@Bean
	fun templateService(): TemplateService {
		return DefaultTemplateAdapter()
	}

	@Bean
	fun tickerClient(restTemplate: RestTemplate): TickerClient {
		return CoinMarketCapAdapter(restTemplate)
	}
}
