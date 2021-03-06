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

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.web.client.RestTemplate

@Configuration
class WebConfig {

	@Value("\${security.encoding-strength}")
	private lateinit var encodingStrength: String

	@Bean
	fun shaPasswordEncoder(): ShaPasswordEncoder {
		return ShaPasswordEncoder(encodingStrength.toInt())
	}

	@Bean
	@Primary
	fun objectMapper() = ObjectMapper().apply {
		registerModule(KotlinModule())
	}

	@Bean
	fun restTemplate(): RestTemplate {
		return RestTemplate()
	}
}
