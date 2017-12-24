package fr.cph.crypto.uuid.jug

import com.fasterxml.uuid.EthernetAddress
import com.fasterxml.uuid.Generators
import com.fasterxml.uuid.NoArgGenerator
import fr.cph.crypto.core.spi.IdGenerator

class Jug : IdGenerator {

    override fun getNewId(): String {
        return generator().generate().toString()
    }

    companion object {
        fun generator(): NoArgGenerator {
            return Generators.timeBasedGenerator(EthernetAddress.fromInterface())
        }
    }
}