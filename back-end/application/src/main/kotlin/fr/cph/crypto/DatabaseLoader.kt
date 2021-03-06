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
package fr.cph.crypto

import fr.cph.crypto.core.entity.Currency
import fr.cph.crypto.core.entity.Position
import fr.cph.crypto.core.entity.Role
import fr.cph.crypto.core.entity.User
import fr.cph.crypto.core.spi.IdGenerator
import fr.cph.crypto.core.spi.TickerRepository
import fr.cph.crypto.core.spi.UserRepository
import fr.cph.crypto.core.usecase.position.AddPosition
import fr.cph.crypto.core.usecase.ticker.UpdateTicker
import fr.cph.crypto.security.PasswordEncoderAdapter
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("dev")
@Component
class DatabaseLoader(private val tickerRepository: TickerRepository,
					 private val userRepository: UserRepository,
					 private val passwordEncoder: PasswordEncoderAdapter,
					 private val idGenerator: IdGenerator,
					 private val addPosition: AddPosition,
					 private val updateTicker: UpdateTicker) : CommandLineRunner {

	override fun run(vararg strings: String) {
		tickerRepository.deleteAll()
		userRepository.deleteAllUsers()
		userRepository.deleteAllPositions()

		updateTicker.updateAll()

		var user = User(id = "1", email = "cp.harmant@gmail.com", password = passwordEncoder.encode("PASSWORD"),
			role = Role.ADMIN, allowed = true)
		user = userRepository.saveUser(user)

		val positionBtc = Position(id = idGenerator.getNewId(), currency1 = Currency.BTC, quantity = 0.06564277, unitCostPrice = 7616.98508457)
		addPosition.addPositionToUser(user.id!!, positionBtc)
		val positionEth = Position(id = idGenerator.getNewId(), currency1 = Currency.ETH, quantity = 1.57128061, unitCostPrice = 318.2)
		addPosition.addPositionToUser(user.id!!, positionEth)
		val positionVtc = Position(id = idGenerator.getNewId(), currency1 = Currency.VTC, quantity = 122.10096277, unitCostPrice = 4.3)
		addPosition.addPositionToUser(user.id!!, positionVtc)
		val positionGrs = Position(id = idGenerator.getNewId(), currency1 = Currency.GRS, quantity = 736.85557018, unitCostPrice = 1.24)
		addPosition.addPositionToUser(user.id!!, positionGrs)
		val positionEthos = Position(id = idGenerator.getNewId(), currency1 = Currency.ETHOS, quantity = 189.81, unitCostPrice = 1.52)
		addPosition.addPositionToUser(user.id!!, positionEthos)
		val positionCardano = Position(id = idGenerator.getNewId(), currency1 = Currency.CARDANO, quantity = 3095.901, unitCostPrice = 0.12)
		addPosition.addPositionToUser(user.id!!, positionCardano)
		val positionPower = Position(id = idGenerator.getNewId(), currency1 = Currency.POWER_LEDGER, quantity = 443.556, unitCostPrice = 0.66)
		addPosition.addPositionToUser(user.id!!, positionPower)
		val positionIcon = Position(id = idGenerator.getNewId(), currency1 = Currency.ICON, quantity = 106.53, unitCostPrice = 5.19)
		addPosition.addPositionToUser(user.id!!, positionIcon)
		val positionMonero = Position(id = idGenerator.getNewId(), currency1 = Currency.MONERO, quantity = 1.2, unitCostPrice = 369.69)
		addPosition.addPositionToUser(user.id!!, positionMonero)
		val positionUtrust = Position(id = idGenerator.getNewId(), currency1 = Currency.UTRUST, quantity = 637.1885736, unitCostPrice = 0.811722)
		addPosition.addPositionToUser(user.id!!, positionUtrust)

		val userFound = userRepository.findOneUserById(user.id!!)!!
		userFound.liquidityMovement = 0.0
		userRepository.saveUser(userFound)
	}
}
