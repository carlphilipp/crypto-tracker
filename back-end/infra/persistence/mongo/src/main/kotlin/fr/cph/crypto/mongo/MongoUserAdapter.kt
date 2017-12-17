package fr.cph.crypto.mongo

import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.mongo.entity.UserDB
import fr.cph.crypto.mongo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class MongoUserAdapter(private val repository: UserRepository) : fr.cph.crypto.core.spi.UserRepository {
    override fun findOneByEmail(email: String): User? {
        return repository.findOneByEmail(email)?.toUser()
    }

    override fun deleteAll() {
        repository.deleteAll()
    }

    override fun save(user: User): User {
        return repository.save(UserDB.from(user)).toUser()
    }

    override fun findOne(id: String): User? {
        return repository.findOne(id)?.toUser()
    }

    override fun findAll(): List<User> {
        return repository.findAll().map { user -> user.toUser() }
    }
}