package fr.cph.crypto.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.User
import fr.cph.crypto.repository.UserRepository
import fr.cph.crypto.service.UserService

@RequestMapping(value = "/user")
@RestController
class UserController @Autowired
constructor(private val repository: UserRepository, private val userService: UserService) {

    val all: List<User>
        @RequestMapping
        get() = repository.findAll().toList()

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun getUser(@PathVariable("id") id: String): User {
        return repository.findOne(id)
    }

    @RequestMapping(value = "/{id}/position/refresh", method = arrayOf(RequestMethod.GET))
    fun refreshUser(@PathVariable("id") id: String): User {
        return userService.refreshUser(id)
    }

    @RequestMapping(value = "/{id}/position/{positionId}", method = arrayOf(RequestMethod.PUT))
    fun updatePosition(@PathVariable("id") id: String, @RequestBody position: Position): Position {
        return userService.updatePosition(position)
    }
}
