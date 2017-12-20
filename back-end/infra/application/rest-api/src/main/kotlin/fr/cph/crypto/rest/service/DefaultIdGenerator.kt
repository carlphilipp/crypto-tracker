package fr.cph.crypto.rest.service

import fr.cph.crypto.core.spi.IdGenerator
import org.springframework.stereotype.Component
import java.util.*

@Component
class DefaultIdGenerator : IdGenerator {

    override fun getNewId(): String {
        return UUID.randomUUID().toString()
    }
}