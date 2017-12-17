package fr.cph.crypto.backend.controller

import fr.cph.crypto.core.api.UserService
import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User
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
    fun getAllUsers(): List<User> {
        return userService.findAll()
    }

    @PreAuthorize("hasAuthority('ADMIN') or authentication.details.decodedDetails['id'] == null")
    @RequestMapping(method = [RequestMethod.POST], produces = ["application/json"])
    fun createUser(@RequestBody user: User): User {
        LOGGER.debug("Create user {}", user)
        return userService.create(user)
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET], produces = ["application/json"])
    fun getUser(@PathVariable("id") id: String): User {
        return userService.findOne(id)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/position"], method = [RequestMethod.POST], consumes = ["application/json"])
    fun addPosition(@PathVariable("id") id: String, @RequestBody position: Position) {
        userService.addPosition(id, position)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id'] and #positionId == #position.id")
    @RequestMapping(value = ["/{id}/position/{positionId}"], method = [RequestMethod.PUT])
    fun updatePosition(@PathVariable("id") id: String,
                       @RequestBody position: Position,
                       @PathVariable("positionId") positionId: String,
                       @RequestParam("transactionQuantity", required = false) transactionQuantity: Double?,
                       @RequestParam("transactionUnitCostPrice", required = false) transactionUnitCostPrice: Double?) {
        userService.updatePosition(id, position, transactionQuantity, transactionUnitCostPrice)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/position/{positionId}/{price}"], method = [RequestMethod.DELETE])
    fun deletePosition(@PathVariable("id") id: String, @PathVariable("positionId") positionId: String, @PathVariable("price") price: Double) {
        userService.deletePosition(id, positionId, price)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/sharevalue"], method = [RequestMethod.GET], produces = ["application/json"])
    fun findAllShareValue(@PathVariable("id") userId: String): List<ShareValue> {
        return userService.findAllShareValue(userId)
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
