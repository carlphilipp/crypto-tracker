package fr.cph.crypto.controller

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User
import fr.cph.crypto.repository.UserRepository
import fr.cph.crypto.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/api/user"])
@RestController
class UserController @Autowired
constructor(private val repository: UserRepository, private val userService: UserService) {

    val all: List<User>
        @PreAuthorize("hasAuthority('ADMIN')")
        @RequestMapping(method = [RequestMethod.GET])
        get() = repository.findAll().toList()

    @RequestMapping(method = [RequestMethod.POST])
    fun createUser(@RequestBody user: User): User {
        return userService.createUser(user)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getUser(@PathVariable("id") id: String): User {
        return repository.findOne(id)
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
