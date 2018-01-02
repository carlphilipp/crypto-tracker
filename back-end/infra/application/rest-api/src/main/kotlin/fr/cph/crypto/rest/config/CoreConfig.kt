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
package fr.cph.crypto.rest.config

import fr.cph.crypto.core.spi.*
import fr.cph.crypto.core.usecase.position.AddPosition
import fr.cph.crypto.core.usecase.position.DeletePosition
import fr.cph.crypto.core.usecase.position.UpdatePosition
import fr.cph.crypto.core.usecase.sharevalue.GetShareValue
import fr.cph.crypto.core.usecase.sharevalue.UpdateShareValue
import fr.cph.crypto.core.usecase.ticker.FindTicker
import fr.cph.crypto.core.usecase.ticker.UpdateTicker
import fr.cph.crypto.core.usecase.user.CreateUser
import fr.cph.crypto.core.usecase.user.FindUser
import fr.cph.crypto.core.usecase.user.ValidateUser
import fr.cph.crypto.core.spi.ContextService
import fr.cph.crypto.core.spi.EmailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfig {

	@Bean
	fun createUser(userRepository: UserRepository,
				   passwordEncoder: PasswordEncoder,
				   idGenerator: IdGenerator,
				   templateService: TemplateService,
				   contextService: ContextService,
				   emailService: EmailService): CreateUser {
		return CreateUser(
				userRepository = userRepository,
				idGenerator = idGenerator,
				passwordEncoder = passwordEncoder,
				templateService = templateService,
				contextService = contextService,
				emailService = emailService)
	}

	@Bean
	fun findUser(userRepository: UserRepository, tickerRepository: TickerRepository): FindUser {
		return FindUser(userRepository = userRepository, tickerRepository = tickerRepository)
	}

	@Bean
	fun addPosition(userRepository: UserRepository, idGenerator: IdGenerator): AddPosition {
		return AddPosition(userRepository = userRepository, idGenerator = idGenerator)
	}

	@Bean
	fun updatePosition(userRepository: UserRepository): UpdatePosition {
		return UpdatePosition(userRepository)
	}

	@Bean
	fun deletePosition(userRepository: UserRepository): DeletePosition {
		return DeletePosition(userRepository)
	}

	@Bean
	fun validateUser(userRepository: UserRepository, passwordEncoder: PasswordEncoder): ValidateUser {
		return ValidateUser(userRepository, passwordEncoder)
	}

	@Bean
	fun findTicker(tickerRepository: TickerRepository): FindTicker {
		return FindTicker(tickerRepository)
	}

	@Bean
	fun updateTicker(tickerRepository: TickerRepository, client: TickerClient): UpdateTicker {
		return UpdateTicker(tickerRepository, client)
	}

	@Bean
	fun updateShareValue(shareValueRepository: ShareValueRepository, userRepository: UserRepository): UpdateShareValue {
		return UpdateShareValue(shareValueRepository, userRepository)
	}

	@Bean
	fun getShareValue(userRepository: UserRepository, shareValueRepository: ShareValueRepository): GetShareValue {
		return GetShareValue(userRepository, shareValueRepository)
	}
}