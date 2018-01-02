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

import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.usecase.user.*
import fr.cph.crypto.rest.dto.PositionDTO
import fr.cph.crypto.rest.dto.ShareValueDTO
import fr.cph.crypto.rest.dto.UserDTO
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/api/user"])
@RestController
class UserController
constructor(private val userService: UserService,
			private val createUser: CreateUser,
			private val findUser: FindUser,
			private val addPosition: AddPosition,
			private val updatePosition: UpdatePosition,
			private val deletePosition: DeletePosition,
			private val getShareValue: GetShareValue,
			private val validateUser: ValidateUser) {

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(method = [RequestMethod.GET], produces = ["application/json"])
	fun getAllUsers(): List<UserDTO> {
		return findUser.findAll().map { user -> UserDTO.from(user) }
	}

	@PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
	@RequestMapping(method = [RequestMethod.POST], produces = ["application/json"])
	fun createUser(@RequestBody user: UserDTO): UserDTO {
		LOGGER.debug("Create user {}", user)
		return UserDTO.from(createUser.create(user.toUser()))
	}

	@PostAuthorize("returnObject.email == authentication.name")
	@RequestMapping(value = ["/{id}"], method = [RequestMethod.GET], produces = ["application/json"])
	fun getUser(@PathVariable("id") id: String): UserDTO {
		return UserDTO.from(findUser.findOne(id))
	}

	@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = ["/{id}/position"], method = [RequestMethod.POST], consumes = ["application/json"])
	fun addPosition(@PathVariable("id") id: String, @RequestBody position: PositionDTO) {
		addPosition.addPositionToUser(id, position.toPosition())
	}

	@PreAuthorize("#id == authentication.details.decodedDetails['id'] and #positionId == #position.id")
	@RequestMapping(value = ["/{id}/position/{positionId}"], method = [RequestMethod.PUT])
	fun updatePosition(@PathVariable("id") id: String,
					   @RequestBody position: PositionDTO,
					   @PathVariable("positionId") positionId: String,
					   @RequestParam("transactionQuantity", required = false) transactionQuantity: Double?,
					   @RequestParam("transactionUnitCostPrice", required = false) transactionUnitCostPrice: Double?) {
		updatePosition.updatePosition(id, position.toPosition(), transactionQuantity, transactionUnitCostPrice)
	}

	@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = ["/{id}/position/{positionId}/{price}"], method = [RequestMethod.DELETE])
	fun deletePosition(@PathVariable("id") id: String,
					   @PathVariable("positionId") positionId: String,
					   @PathVariable("price") price: Double) {
		deletePosition.deletePosition(id, positionId, price)
	}

	@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = ["/{id}/position/{positionId}/fee/{feeAmount}"], method = [RequestMethod.POST])
	fun addFeeToPosition(@PathVariable("id") id: String,
						 @PathVariable("positionId") positionId: String,
						 @PathVariable("feeAmount") feeAmount: Double) {
		updatePosition.addFeeToPosition(id, positionId, feeAmount)
	}

	@PreAuthorize("#id == authentication.details.decodedDetails['id']")
	@RequestMapping(value = ["/{id}/sharevalue"], method = [RequestMethod.GET], produces = ["application/json"])
	fun findAllShareValue(@PathVariable("id") id: String): List<ShareValueDTO> {
		return getShareValue.findAllShareValue(id).map { shareValue -> ShareValueDTO.from(shareValue) }
	}

	// TODO: delete that endpoint when share value dev is done
	@RequestMapping(value = ["/share"])
	fun updateAllUsersShareValue() {
		userService.updateAllUsersShareValue()
	}

	@PreAuthorize("authentication.details.decodedDetails['id'] == null")
	@RequestMapping(value = ["{id}/validate/{key}"])
	fun validateUser(@PathVariable("id") id: String, @PathVariable("key") key: String) {
		validateUser.validateUser(id, key)
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(UserController::class.java)
	}
}
