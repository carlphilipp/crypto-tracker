package fr.cph.crypto.mongo

import fr.cph.crypto.core.api.entity.Position
import fr.cph.crypto.mongo.entity.PositionDB
import fr.cph.crypto.mongo.repository.PositionRepository
import org.springframework.stereotype.Service

@Service
class MongoPositionAdapter(private val repository: PositionRepository) : fr.cph.crypto.core.spi.PositionRepository {

    override fun save(position: Position): Position {
        return repository.save(PositionDB.from(position)).toPosition()
    }

    override fun delete(id: String) {
        return repository.delete(id)
    }

    override fun deleteAll() {
        repository.deleteAll()
    }
}