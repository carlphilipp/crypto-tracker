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
package fr.cph.crypto.service

import fr.cph.crypto.core.spi.ContextService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ContextServiceAdapter : ContextService {

	@Value("\${context.scheme}")
	private lateinit var scheme: String

	@Value("\${context.host}")
	private lateinit var host: String

	@Value("\${context.port}")
	private lateinit var port: String

	override fun getBaseUrl(): String {
		return if (scheme == "https") {
			"$scheme://$host"
		} else {
			"$scheme://$host:$port"
		}
	}
}
