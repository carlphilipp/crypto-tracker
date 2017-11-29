package fr.cph.crypto.controller

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User
import fr.cph.crypto.repository.UserRepository
import fr.cph.crypto.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping(value = ["/api/user"])
@RestController
class UserController @Autowired
constructor(private val repository: UserRepository, private val userService: UserService) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = [RequestMethod.GET])
    fun getAllUsers(): List<User> {
        return repository.findAll().toList()
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun createUser(@RequestBody user: User): User {
        return userService.createUser(user)
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getUser(@PathVariable("id") id: String): User {
        return repository.findOne(id)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/position/refresh"], method = [RequestMethod.GET])
    fun refreshUser(@PathVariable("id") id: String): List<Position> {
        return userService.refreshUserPositions(id)
    }

    @PreAuthorize("#id == authentication.details.decodedDetails['id']")
    @RequestMapping(value = ["/{id}/position/{positionId}"], method = [RequestMethod.PUT])
    fun updatePosition(@PathVariable("id") id: String, @RequestBody position: Position, auth: Authentication, principal: Principal): Position {
        // TODO verify that the position is own by that user
        return userService.updatePosition(position)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UserController::class.java)
    }
}
