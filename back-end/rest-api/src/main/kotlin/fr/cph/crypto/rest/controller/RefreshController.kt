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
package fr.cph.crypto.rest.controller

import fr.cph.crypto.core.entity.Currency
import fr.cph.crypto.core.spi.TickerClient
import fr.cph.crypto.core.spi.TickerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class RefreshController(private val tickerRepository: TickerRepository, private val client: TickerClient) {

	@PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
	@RequestMapping(value = ["/api/refresh"], method = [RequestMethod.GET], produces = ["application/json"])
	fun refreshAll(): ResponseEntity<String> {
		client.getTickers(Currency.USD, Currency.cryptoCurrenciesAsListOfString())
			.forEach { ticker -> tickerRepository.save(ticker) }
		return ResponseEntity("{}", HttpStatus.OK)
	}
}
