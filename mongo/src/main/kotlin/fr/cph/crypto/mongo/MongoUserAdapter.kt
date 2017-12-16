package fr.cph.crypto.mongo

import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.mongo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class MongoUserAdapter(private val repository: UserRepository) : fr.cph.crypto.core.spi.UserRepository {
    override fun findOneByEmail(email: String): User? {
        return repository.findOneByEmail(email)
    }

    override fun deleteAll() {
        repository.deleteAll()
    }

    override fun save(user: User): User {
        return repository.save(user)
    }

    override fun findOne(id: String): User? {
        return repository.findOne(id)
    }

    override fun findAll(): List<User> {
        return repository.findAll()
    }
}