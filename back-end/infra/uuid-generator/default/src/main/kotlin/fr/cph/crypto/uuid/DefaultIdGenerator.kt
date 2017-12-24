package fr.cph.crypto.uuid

import fr.cph.crypto.core.spi.IdGenerator
import java.util.*

class DefaultIdGenerator : IdGenerator {

    override fun getNewId(): String {
        return UUID.randomUUID().toString()
    }
}