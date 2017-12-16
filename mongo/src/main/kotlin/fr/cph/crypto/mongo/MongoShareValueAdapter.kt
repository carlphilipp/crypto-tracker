package fr.cph.crypto.mongo

import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.mongo.repository.ShareValueRepository
import org.springframework.stereotype.Service

@Service
class MongoShareValueAdapter(private val repository: ShareValueRepository) : fr.cph.crypto.core.spi.ShareValueRepository {

    override fun findAllByUser(user: User): List<ShareValue> {
        return repository.findAllByUser(user)
    }

    override fun findTop1ByUserOrderByTimestampDesc(user: User): ShareValue? {
        return repository.findTop1ByUserOrderByTimestampDesc(user)
    }

    override fun save(shareValue: ShareValue): ShareValue {
        return repository.save(shareValue)
    }
}