package fr.cph.crypto.backend.controller

import fr.cph.crypto.backend.dto.PositionDTO
import fr.cph.crypto.backend.dto.ShareValueDTO
import fr.cph.crypto.backend.dto.UserDTO
import fr.cph.crypto.core.api.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/api/user"])
@RestController
class UserController
constructor(private val userService: UserService) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = [RequestMethod.GET], produces = ["application/json"])
    fun getAllUsers(): List<UserDTO> {
        return userService.findAll().map { user -> UserDTO.from(user) }
    }

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(method = [RequestMethod.POST], produces = ["application/json"])
    fun createUser(@RequestBody user: UserDTO): UserDTO {
        LOGGER.debug("Create user {}", user)
        return UserDTO.from(userService.create(user.toUser()))
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getUser(@PathVariable("id") id: String): UserDTO {
        return UserDTO.from(userService.findOne(id))
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/position"], method = [RequestMethod.POST], consumes = ["application/json"])
    fun addPosition(@PathVariable("id") id: String, @RequestBody position: PositionDTO) {
        userService.addPosition(id, position.toPosition())
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id'] and #positionId == #position.id")
    @RequestMapping(value = ["/{id}/position/{positionId}"], method = [RequestMethod.PUT])
    fun updatePosition(@PathVariable("id") id: String,
                       @RequestBody position: PositionDTO,
                       @PathVariable("positionId") positionId: String,
                       @RequestParam("transactionQuantity", required = false) transactionQuantity: Double?,
                       @RequestParam("transactionUnitCostPrice", required = false) transactionUnitCostPrice: Double?) {
        userService.updatePosition(id, position.toPosition(), transactionQuantity, transactionUnitCostPrice)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/position/{positionId}/{price}"], method = [RequestMethod.DELETE])
    fun deletePosition(@PathVariable("id") id: String,
                       @PathVariable("positionId") positionId: String,
                       @PathVariable("price") price: Double) {
        userService.deletePosition(id, positionId, price)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/sharevalue"], method = [RequestMethod.GET], produces = ["application/json"])
    fun findAllShareValue(@PathVariable("id") id: String): List<ShareValueDTO> {
        return userService.findAllShareValue(id).map { shareValue -> ShareValueDTO.from(shareValue) }
    }

    // TODO: delete that endpoint when share value dev is done
    @RequestMapping(value = ["/share"])
    fun updateAllUsersShareValue() {
        userService.updateAllUsersShareValue()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserController::class.java)
    }
}
