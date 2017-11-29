package fr.cph.crypto.controller

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User
import fr.cph.crypto.repository.UserRepository
import fr.cph.crypto.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping(value = ["/api/user"])
@RestController
class UserController @Autowired
constructor(private val repository: UserRepository, private val userService: UserService) {

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = [RequestMethod.GET])
    fun getAllUsers(@RequestHeader(value = "Authorization") bearerToken: String): List<User> {
        return repository.findAll().toList()
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun createUser(@RequestBody user: User): User {
        return userService.createUser(user)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getUser(@PathVariable("id") id: String, principal: Principal): User {
        val user = repository.findOne(id)
        if (principal.name == user.email) {
            return user
        } else {
            throw RuntimeException()
        }
    }

    @RequestMapping(value = ["/{id}/position/refresh"], method = [RequestMethod.GET])
    fun refreshUser(@PathVariable("id") id: String): List<Position> {
        return userService.refreshUserPositions(id)
    }

    @RequestMapping(value = ["/{id}/position/{positionId}"], method = [RequestMethod.PUT])
    fun updatePosition(@PathVariable("id") id: String, @RequestBody position: Position): Position {
        return userService.updatePosition(position)
    }
}
