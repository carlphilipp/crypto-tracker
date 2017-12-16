package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.Position

interface PositionRepository {

    fun save(position: Position): Position

    fun delete(id: String)

    fun deleteAll()
}