package fr.cph.crypto.uuid.mongo

import fr.cph.crypto.core.spi.IdGenerator
import org.bson.types.ObjectId

class MongoIdGenerator : IdGenerator {
    override fun getNewId(): String {
        return ObjectId.get().toString()
    }
}
