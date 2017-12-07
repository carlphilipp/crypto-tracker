package fr.cph.crypto.backend.controller

import fr.cph.crypto.backend.domain.Position
import fr.cph.crypto.backend.domain.User
import fr.cph.crypto.backend.service.UserService
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
    @RequestMapping(value = ["/{id}/position"], method = [RequestMethod.POST], produces = ["application/json"])
    fun addPosition(@PathVariable("id") id: String, @RequestBody position: Position): User {
        return userService.addPosition(id, position)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id'] and #positionId == #position.id")
    @RequestMapping(value = ["/{id}/position/{positionId}"], method = [RequestMethod.PUT], produces = ["application/json"])
    fun updatePosition(@PathVariable("id") id: String, @RequestBody position: Position, @PathVariable("positionId") positionId: String): Position {
        return userService.updatePosition(id, position)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/position/{positionId}"], method = [RequestMethod.DELETE])
    fun deletePosition(@PathVariable("id") id: String, @PathVariable("positionId") positionId: String) {
        userService.deletePosition(id, positionId)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserController::class.java)
    }
}
