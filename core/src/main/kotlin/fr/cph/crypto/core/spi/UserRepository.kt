package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.User

interface UserRepository {
    fun findOneByEmail(email: String): User?

    fun deleteAll()

    fun save(user: User): User

    fun findOne(id: String): User?

    fun findAll(): List<User>
}