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
package fr.cph.crypto.rest.controller

import fr.cph.crypto.rest.dto.TickerDTO
import fr.cph.crypto.core.api.TickerService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["/api/ticker"])
@RestController
class TickerController
constructor(private val tickerService: TickerService) {

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(method = [RequestMethod.GET], produces = ["application/json"])
    fun getAllTickers(): List<TickerDTO> {
        return tickerService.findAll().map { ticker -> TickerDTO.from(ticker) }
    }

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(value = ["/{currency1}/{currency2}"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getTicker(@PathVariable("currency1") currencyCode1: String,
                  @PathVariable("currency2") currencyCode2: String): TickerDTO {
        return TickerDTO.from(tickerService.findOne(currencyCode1 + "-" + currencyCode2))
    }
}
